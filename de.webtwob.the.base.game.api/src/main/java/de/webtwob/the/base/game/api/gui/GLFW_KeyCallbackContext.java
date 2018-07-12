package de.webtwob.the.base.game.api.gui;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class GLFW_KeyCallbackContext {

    public final GLFW_Window window;
    public final  int         key;
    public final int         scancode;
    public final  int         action;
    public final int         mod;

    GLFW_KeyCallbackContext(final long windowId, final int key, final int scancode, final int action, final int mod) {
        window = GLFW_Window.getWindowForId(windowId);
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mod = mod;
    }
}
