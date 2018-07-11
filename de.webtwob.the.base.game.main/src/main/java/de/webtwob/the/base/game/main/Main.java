package de.webtwob.the.base.game.main;

import de.webtwob.the.base.game.api.MainThreadQueue;
import de.webtwob.the.base.game.main.client.MainClient;
import de.webtwob.the.base.game.main.server.MainServer;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.stream.Stream;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        MainThreadQueue.MAIN_THREAD_ID = Thread.currentThread().getId();

        try (var cb = GLFWErrorCallback.createPrint(System.err)) {

            cb.set();

            if (Stream.of(args).anyMatch("-server"::equals)) {
                MainServer.main(args);
            } else {
                MainClient.main(args);
            }

            MainThreadQueue.runMainThreadQueue();
            glfwTerminate();
            glfwSetErrorCallback(null);
        }
    }

}
