package de.webtwob.the.base.game.api.internal;

import de.webtwob.the.base.game.api.gui.Color;
import de.webtwob.the.base.game.api.gui.ICurrentContext;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class GLFW_WindowContext implements ICurrentContext {

    private final long restore;

    private boolean     closed = false;
    private GLFW_Window window;

    public GLFW_WindowContext(GLFW_Window window) {
        this.window = window;
        restore = glfwGetCurrentContext();
        if (restore != window.getWindowId()) {
            glfwMakeContextCurrent(window.getWindowId());
        }
    }

    @Override
    public void setClearColor(Color c) {
        glClearColor(c.red, c.green, c.blue, c.alpha);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void swapBuffer() {
        glfwSwapBuffers(window.getWindowId());
    }

    @Override
    public void setSwapInterval(int interval) {
        glfwSwapInterval(interval);
    }

    @Override
    public void close() {
        if (!closed) {
            if (window.getWindowId() != restore) {
                glfwMakeContextCurrent(restore);
            }
            closed = true;
        }
    }
}
