package de.webtwob.the.base.game.api;

import de.webtwob.the.base.game.api.gui.GLFW_MainThreadContext;
import de.webtwob.the.base.game.api.gui.GLFW_Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public interface IRenderer {

    System.Logger LOGGER = System.getLogger(IRenderer.class.getName());

    default void init(GLFW_Window window) {

        window.runWithMainThreadContext(main -> {
            main.setKeyCallback(context -> {
                if(context.key == GLFW_KEY_ESCAPE && context.action == GLFW_RELEASE){
                    context.window.setShouldClose(true);
                }
            });
        });

        GL.createCapabilities();
    }

    void render(GLFW_Window window);

    void clear(GLFW_Window window);

    default void runRenderLoop(GLFW_Window window) {
        LOGGER.log(System.Logger.Level.INFO, "Initializing Renderer!");

        try (var context = window.makeContextCurrent()) {
            init(window);
            while (!Thread.interrupted() && !window.runWithMainThreadContext(
                    GLFW_MainThreadContext::shouldClose)) {
                handleInput(window);
                render(window);
            }
        }
        LOGGER.log(System.Logger.Level.INFO, "Renderer finished!");
    }

    void handleInput(GLFW_Window window);
}
