package de.webtwob.the.base.game.main.client;

import de.webtwob.the.base.game.api.gui.GLFW_MainThreadContext;
import de.webtwob.the.base.game.api.gui.GLFW_Window;
import de.webtwob.the.base.game.api.service.IBaseMod;

import java.util.ServiceLoader;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class MainClient {

    private GLFW_Window window;

    private System.Logger logger = System.getLogger(MainClient.class.getName());

    public static void main(String[] args) {

        new MainClient().run();

    }

    private void run() {

        logger.log(System.Logger.Level.TRACE, "Starting Client");

        window = GLFW_Window.createWindow("The Base Game!");

        window.runWithMainThreadContext((GLFW_MainThreadContext main) -> main.setVisible(true));

        ServiceLoader.load(IBaseMod.class).findFirst().ifPresentOrElse(mod -> {
            logger.log(System.Logger.Level.INFO, () -> "Choose Base Mod: " + mod.getModId());

            var renderThread = new ClientRenderThread();

            renderThread.setChild(mod.getClientInstance().getRenderer());

            renderThread.start(window);

            logger.log(System.Logger.Level.INFO, "Client Running!");

        }, () -> {
            logger.log(System.Logger.Level.ERROR, "No IBaseMod found!");
            System.exit(0);
        });

    }

}
