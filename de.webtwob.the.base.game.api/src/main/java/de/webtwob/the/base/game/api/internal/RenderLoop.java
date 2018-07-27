package de.webtwob.the.base.game.api.internal;

import de.webtwob.the.base.game.api.MainThreadQueue;
import de.webtwob.the.base.game.api.gui.Color;
import de.webtwob.the.base.game.api.gui.ICurrentContext;
import de.webtwob.the.base.game.api.gui.IMainThreadContext;
import de.webtwob.the.base.game.api.interfaces.IRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class RenderLoop implements Runnable {

    private static final System.Logger LOGGER    = System.getLogger(RenderLoop.class.getName());
    private final        GLFW_Window   window;
    private final        Object        childLock = new Object();
    private              IRenderer     replaceChild; //used to replace child as this needs to be done by the render Thread
    private              IRenderer     child; //DO NOT EDIT DIRECTLY unless you are the RenderLoop!!!!

    public RenderLoop(final GLFW_Window window) {
        this.window = window;
    }

    @Override
    public void run() {
        runRenderLoop(window);
    }

    private void init() {
        GL.createCapabilities();
        LOGGER.log(System.Logger.Level.INFO, () -> "GL Version " + glGetString(GL_VERSION));
        LOGGER.log(System.Logger.Level.INFO, () -> "GL SL Version " + glGetString(GL_SHADING_LANGUAGE_VERSION));
    }

    private void runRenderLoop(final GLFW_Window window) {
        LOGGER.log(System.Logger.Level.INFO, () -> "Initializing RenderLoop for window " + window.getWindowId());

        try (var context = window.makeContextCurrent()) {
            init();

            long start = System.nanoTime();
            long end   = start;
            long delta;
            while (!Thread.interrupted() && !window.runWithMainThreadContext(IMainThreadContext::shouldClose)) {
                delta = end - start;
                start = System.nanoTime();
                update(context, delta);
                render(context);
                end = System.nanoTime();
            }
        }finally{
            destroy();
        }

        window.runWithMainThreadContext(IMainThreadContext::destroy);
        LOGGER.log(System.Logger.Level.INFO, "RenderLoop for window " + window.getWindowId() + " finished!");
    }

    private void update(final ICurrentContext context, final long delta) {
        MainThreadQueue.waitAndInvoke(GLFW::glfwPollEvents);

        if (replaceChild != child) {
            synchronized (childLock) {
                if (replaceChild != child) {
                    if (child != null) {
                        child.destroy(window);
                    }
                    if (replaceChild != null) {
                        replaceChild.init(window, context);
                    }
                    child = replaceChild;
                }
            }
        }
        if (child != null) {
            child.update(window, context, delta);
        }
    }

    private void render(final ICurrentContext context) {

        context.setClearColor(Color.RED);

        if (child != null) {
            child.render(window, context);
        }

        context.swapBuffer();

        window.runWithMainThreadContext(GLFW::glfwPollEvents);

    }

    private void destroy() {
        if(child!=null)
            child.destroy(window);
    }

    public void setChild(IRenderer renderer) {
        synchronized (childLock) {
            replaceChild = renderer;
        }
    }

}
