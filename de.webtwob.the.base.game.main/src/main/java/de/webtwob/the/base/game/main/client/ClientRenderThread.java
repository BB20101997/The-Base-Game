package de.webtwob.the.base.game.main.client;

import de.webtwob.the.base.game.api.IRenderer;
import de.webtwob.the.base.game.api.gui.GLFW_MainThreadContext;
import de.webtwob.the.base.game.api.gui.GLFW_Window;

import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class ClientRenderThread implements IRenderer {

    IRenderer child;
    private Thread renderThread;

    @Override
    public void render(final GLFW_Window window) {
        if (child != null) {
            child.runRenderLoop(window);
        }
        try (var context = window.makeContextCurrent()) {

            glClearColor(1f, 0f, 0f, 0f);

            clear(window);

            context.swapBuffer();

            window.runWithMainThreadContext((Consumer<GLFW_MainThreadContext>) c -> glfwPollEvents());

        }
    }

    @Override
    public void destroy(final GLFW_Window window) {
        window.runWithMainThreadContext(GLFW_MainThreadContext::destroy);
        System.exit(0);
    }

    public void setChild(IRenderer renderer) {
        if (child != null) {
            throw new IllegalStateException("ClientRendererThread already has a child!");
        }
        child = renderer;
    }

    public void start(GLFW_Window window) {
        if (renderThread == null) {
            renderThread = new Thread(() -> this.runRenderLoop(window));
            renderThread.setDaemon(false);
            renderThread.setName("CLIENT-RENDER-THREAD");
            renderThread.setUncaughtExceptionHandler((t, e) -> {
                window.runWithMainThreadContext(GLFW_MainThreadContext::destroy);
                System.exit(1);
            });
            renderThread.start();
        }
    }
}
