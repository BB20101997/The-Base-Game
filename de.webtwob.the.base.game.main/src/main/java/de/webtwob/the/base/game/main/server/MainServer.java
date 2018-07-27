package de.webtwob.the.base.game.main.server;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class MainServer implements Runnable {

    private static final System.Logger LOGGER = System.getLogger(MainServer.class.getName());

    @Override
    public void run() {
        LOGGER.log(System.Logger.Level.INFO, "This is Server!");
    }
}
