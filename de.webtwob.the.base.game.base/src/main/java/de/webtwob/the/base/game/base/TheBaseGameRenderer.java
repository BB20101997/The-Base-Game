package de.webtwob.the.base.game.base;

import de.webtwob.the.base.game.api.IRenderer;
import de.webtwob.the.base.game.api.gui.Color;
import de.webtwob.the.base.game.api.gui.GLFW_KeyCallbackContext;
import de.webtwob.the.base.game.api.gui.GLFW_Window;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class TheBaseGameRenderer implements IRenderer {

    private int r;
    private int g;
    private int b;
    private int a;

    @Override
    public void init(GLFW_Window window) {
        IRenderer.super.init(window);
        window.setBackgroundColor(new Color(0,0,0,0));
    }

    @Override
    public void render(GLFW_Window window) {

        try (var context = window.makeContextCurrent()) {

            var bg = window.getBackgroundColor();

            glClearColor(bg.red, bg.green, bg.blue, bg.alpha);

            clear(window);

            context.swapBuffer();
        }

    }

    @Override
    public void keyCallBack(GLFW_KeyCallbackContext context) {
        IRenderer.super.keyCallBack(context);
        if (context.action == GLFW.GLFW_RELEASE) {
            boolean changed = false;
            switch (context.key) {
                case GLFW.GLFW_KEY_G:
                    changed = true;
                    g++;
                    g%=9;
                    break;
                case GLFW.GLFW_KEY_R:
                    changed = true;
                    r++;
                    r%=9;
                    break;
                case GLFW.GLFW_KEY_B:
                    changed = true;
                    b++;
                    b%=9;
                    break;
                case GLFW.GLFW_KEY_A:
                    changed = true;
                    a++;
                    a%=9;
                    break;
                case GLFW.GLFW_KEY_0:
                    changed = true;
                    r=g=b=a=0;
                    break;
                default:
                    break;
            }
            if(changed) {
                context.window.setBackgroundColor(new Color(r * 0.125f, g * 0.125f, b * 0.125f, a * 0.125f));
            }
        }
    }
}
