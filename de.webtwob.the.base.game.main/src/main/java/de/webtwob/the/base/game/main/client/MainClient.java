package de.webtwob.the.base.game.main.client;

import de.webtwob.the.base.game.api.gui.IMainThreadContext;
import de.webtwob.the.base.game.api.gui.IWindow;
import de.webtwob.the.base.game.api.service.IBaseModFactory;

import java.util.ServiceLoader;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class MainClient implements Runnable{

    private IWindow window;

    private static final System.Logger LOGGER = System.getLogger(MainClient.class.getName());

    public void run() {

        LOGGER.log(System.Logger.Level.TRACE, "Starting Client");

        window = IWindow.createWindow("The Base Game!");

        window.runWithMainThreadContext((IMainThreadContext main) -> main.setVisible(true));

        ServiceLoader.load(IBaseModFactory.class).findFirst().ifPresentOrElse(mod -> {
            LOGGER.log(System.Logger.Level.INFO, () -> "Choose Base Mod: " + mod.get().id());

            //window.setMainRenderer(mod.get().getClientInstance().getRenderer());

            window.setMainRenderer(new MainMenuRenderer());

            LOGGER.log(System.Logger.Level.INFO,"Starting Render Thread");

            window.runWithMainThreadContext(IMainThreadContext::startRenderLoop);

            LOGGER.log(System.Logger.Level.INFO, "Client Running!");

        }, () -> {
            LOGGER.log(System.Logger.Level.ERROR, "No IBaseModFactory found!");
            System.exit(0);
        });

    }

}
