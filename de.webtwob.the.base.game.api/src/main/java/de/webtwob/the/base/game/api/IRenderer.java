package de.webtwob.the.base.game.api;

import de.webtwob.the.base.game.api.gui.GLFW_MainThreadContext;
import de.webtwob.the.base.game.api.gui.GLFW_Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public interface IRenderer {

    System.Logger LOGGER = System.getLogger(IRenderer.class.getName());

    /**
     * @return true if this renderer want's to pass the context to it's parent
     */
    default boolean exitToParentRenderer() {
        return false;
    }

    default void init(GLFW_Window window) {

        window.runWithMainThreadContext(main -> {
            main.setKeyCallback(context -> {
                if (context.key == GLFW_KEY_ESCAPE && context.action == GLFW_RELEASE) {
                    context.window.setShouldClose(true);
                }
            });
        });

        GL.createCapabilities();
    }

    default void destroy(GLFW_Window window){

    }

    void render(GLFW_Window window);

    default void clear(GLFW_Window window) {
        try (var ignored = window.makeContextCurrent()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        }
    }

    default void runRenderLoop(GLFW_Window window) {
        LOGGER.log(System.Logger.Level.INFO, "Initializing Renderer!");

        try (var context = window.makeContextCurrent()) {
            init(window);
            while (!Thread.interrupted() && !window.runWithMainThreadContext(
                    GLFW_MainThreadContext::shouldClose) && !exitToParentRenderer()) {
                handleInput(window);
                render(window);
            }
            destroy(window);
        }
        LOGGER.log(System.Logger.Level.INFO, "Renderer finished!");
    }

    default void handleInput(GLFW_Window window){
        MainThreadQueue.waitAndInvoke(GLFW::glfwPollEvents);
    }
}
