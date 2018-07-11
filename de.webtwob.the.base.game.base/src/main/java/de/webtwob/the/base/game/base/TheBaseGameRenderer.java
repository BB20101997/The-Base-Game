package de.webtwob.the.base.game.base;

import de.webtwob.the.base.game.api.IRenderer;
import de.webtwob.the.base.game.api.gui.GLFW_Window;

import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class TheBaseGameRenderer implements IRenderer {

    @Override
    public void render(GLFW_Window window) {

        try (var context = window.makeContextCurrent()) {

            glClearColor(1f, 0f, 0f, 0f);

            clear(window);

            context.swapBuffer();
        }
    }

}
