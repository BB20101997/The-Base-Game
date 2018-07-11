package de.webtwob.the.base.game.api.gui;

import de.webtwob.the.base.game.api.util.SafeAutoCloseable;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class GLFW_WindowContext implements SafeAutoCloseable {

    long restore;

    boolean closed = false;
    private GLFW_Window window;

    GLFW_WindowContext(final GLFW_Window window) {
        restore = glfwGetCurrentContext();
        if (restore != window.windowId) {
            glfwMakeContextCurrent(window.windowId);
        }
        this.window = window;
    }

    public void swapBuffer() {
        glfwSwapBuffers(window.windowId);
    }

    public void setSwapIntervall(int intervall) {
        glfwSwapInterval(intervall);
    }

    @Override
    public void close() {
        if (!closed) {
            if (window.windowId != restore) {
                glfwMakeContextCurrent(restore);
            }
            closed = true;
        }
    }
}
