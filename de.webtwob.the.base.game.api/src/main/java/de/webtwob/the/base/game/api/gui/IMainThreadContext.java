package de.webtwob.the.base.game.api.gui;

/**
 * Created by BB20101997 on 21. Jul. 2018.
 */
public interface IMainThreadContext {

    boolean shouldClose();

    void startRenderLoop();

    void stopRenderLoop();

    void destroy();

    void setVisible(boolean visible);

    boolean isKeyPressed(int keyCode);

    int[] getScreenSize();

    void setScreenPosition(int x, int y);
}
