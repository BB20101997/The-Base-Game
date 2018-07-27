package de.webtwob.the.base.game.api.internal;

import de.webtwob.the.base.game.api.gui.IMainThreadContext;
import org.lwjgl.glfw.Callbacks;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by BB20101997 on 21. Jul. 2018.
 */
public class GLFW_MainThreadContext implements IMainThreadContext {


    private static final System.Logger LOGGER = System.getLogger(GLFW_MainThreadContext.class.getName());

    private final GLFW_Window window;

    public GLFW_MainThreadContext(GLFW_Window window){
        this.window = window;
    }

@Override
    public boolean shouldClose() {
        return glfwWindowShouldClose(window.getWindowId());
    }

    @Override
    public void startRenderLoop() {
        if (window.renderThread == null || window.renderThread.getState() == Thread.State.TERMINATED) {
            var thread = window.renderThread = new Thread(window.renderLoop);
            thread.setName("RenderLoop for Window " + window.getWindowId());
            thread.setDaemon(true);
            thread.setUncaughtExceptionHandler((t, e) -> {
                //restart Thread if not ThreadDeath exception or interrupted
                if (!((e instanceof ThreadDeath) || t.isInterrupted())) {
                    LOGGER.log(System.Logger.Level.ERROR,"RenderLoop died because of an Exception Restarting Render Loop",e);
                    window.runWithMainThreadContext(IMainThreadContext::startRenderLoop);
                }else{
                    window.runWithMainThreadContext(IMainThreadContext::destroy);
                    System.exit(0);
                }
            });
            thread.start();
        }
    }

    @Override
    public void stopRenderLoop() {
        var thread = window.renderThread;
        if (thread!=null && thread.getState() != Thread.State.TERMINATED) {
            thread.interrupt();
        }
    }

    @Override
    public void destroy() {
        GLFW_Window.windowMap.remove(window.getWindowId());
        Callbacks.glfwFreeCallbacks(window.getWindowId());
        setVisible(false);
        glfwDestroyWindow(window.getWindowId());
        window.removeAllListeners();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            glfwShowWindow(window.getWindowId());
        } else {
            glfwHideWindow(window.getWindowId());
        }
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(window.getWindowId(), keyCode) == GLFW_PRESS;
    }

    @Override
    public int[] getScreenSize() {
        var pWidth  = new int[1];
        var pHeight = new int[1];

        glfwGetWindowSize(window.getWindowId(), pWidth, pHeight);

        return new int[]{pWidth[0], pHeight[0]};
    }

    @Override
    public void setScreenPosition(int x, int y) {
        glfwSetWindowPos(window.getWindowId(), x, y);
    }

}
