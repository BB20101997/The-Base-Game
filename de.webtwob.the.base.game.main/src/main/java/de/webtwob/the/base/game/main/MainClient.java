package de.webtwob.the.base.game.main;

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

            var renderer = mod.getClientInstance().getRenderer();

            var renderThread = new Thread(() -> renderer.runRenderLoop(window));
            renderThread.setDaemon(false);
            renderThread.setName("CLIENT-RENDER-THREAD");
            renderThread.start();
            logger.log(System.Logger.Level.INFO, "Client Running!");

            new Thread(() -> {
                while (renderThread.isAlive()) {
                    try {
                        renderThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                window.runWithMainThreadContext(GLFW_MainThreadContext::destroy);
                System.exit(0);
            }).start();
        }, () -> {
            logger.log(System.Logger.Level.ERROR, "No IBaseMod found!");
            System.exit(0);
        });

    }

    @SuppressWarnings({"squid:S2095", "squid:S106"})
    private void init() {
    }

}
