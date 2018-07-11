package de.webtwob.the.base.game.api.gui;

import de.webtwob.the.base.game.api.util.RequireMainThread;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
@RequireMainThread
public class GLFW_MainThreadContext {

    private GLFW_Window window;

    public GLFW_MainThreadContext(GLFW_Window window) {this.window = window;}

    public boolean shouldClose() {
        return glfwWindowShouldClose(window.windowId);
    }

    public void destroy() {
        Callbacks.glfwFreeCallbacks(window.windowId);
        glfwDestroyWindow(window.windowId);
    }

    public void setVisible(boolean visible) {
        if (visible) {
            glfwShowWindow(window.windowId);
        } else {
            glfwHideWindow(window.windowId);
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(window.windowId, keyCode) == GLFW_PRESS;
    }

    public GLFWKeyCallback setKeyCallback(Consumer<GLFW_KeyCallbackContext> callback) {
        return glfwSetKeyCallback(window.windowId, (wid,key,sc,act,mod)->callback.accept(new GLFW_KeyCallbackContext(wid,key,sc,act,mod)));
    }

    public int[] getScreenSize() {
        var pWidth  = new int[1];
        var pHeight = new int[1];

        glfwGetWindowSize(window.windowId, pWidth, pHeight);

        return new int[]{pWidth[0], pHeight[0]};
    }

    public void setScreenPosition(int x, int y) {
        glfwSetWindowPos(window.windowId, x, y);
    }
}
