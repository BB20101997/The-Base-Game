package de.webtwob.the.base.game.base;

import de.webtwob.the.base.game.api.IRenderer;
import de.webtwob.the.base.game.api.gui.GLFW_MainThreadContext;
import de.webtwob.the.base.game.api.gui.GLFW_Window;

import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class TheBaseGameRenderer implements IRenderer {

    @Override
    public void render(GLFW_Window window) {

        try (var context = window.makeContextCurrent()) {

            glClearColor(1f, 0f, 0f, 0f);

            clear(window);

            /*
            float[] vertexes = new float[]{
                    0.0f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f
            };

            var buffer = BufferUtils.createFloatBuffer(vertexes.length);

            buffer.put(vertexes);
            */

            context.swapBuffer();

            window.runWithMainThreadContext((Consumer<GLFW_MainThreadContext>) c->glfwPollEvents());

        }
    }



    @Override
    public void clear(GLFW_Window window) {
        try (var ignored = window.makeContextCurrent()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        }
    }

    @Override
    public void handleInput(final GLFW_Window window) {
        glfwPollEvents();

    }

}
