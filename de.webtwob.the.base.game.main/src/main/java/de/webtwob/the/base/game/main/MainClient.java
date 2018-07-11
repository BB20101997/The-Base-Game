package de.webtwob.the.base.game.main;

import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class MainClient {

    private long window;

    public static void main(String[] args) {

        new MainClient().run();

    }

    private void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();
        Callbacks.glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    private void loop() {
        GL.createCapabilities();

        glClearColor(1f, 0f, 0f, 0f);

        while (!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window);

            glfwPollEvents();
        }

    }


    @SuppressWarnings({"squid:S2095","squid:S106"})
    private void init() {

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to init GLFW!");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        window = glfwCreateWindow(300, 300, "The Base Game!", NULL, NULL);

        if (window == NULL) {
            throw new RuntimeException("Window creation failed!");
        }

        glfwSetKeyCallback(window, (window, key, scancode, action, mode) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (vidMode != null) {
                glfwSetWindowPos(window, (vidMode.width() - pWidth.get(0)) / 2,
                                 (vidMode.height() - pHeight.get(0)) / 2
                );
            }

        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(2);
        glfwShowWindow(window);

    }

}
