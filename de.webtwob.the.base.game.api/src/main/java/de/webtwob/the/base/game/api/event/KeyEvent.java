package de.webtwob.the.base.game.api.event;

import de.webtwob.the.base.game.api.gui.IWindow;
import de.webtwob.the.base.game.api.interfaces.WindowEvent;

/**
 * Created by BB20101997 on 18. Jul. 2018.
 */
public class KeyEvent implements WindowEvent {

    private final IWindow window;
    public final  int     key;
    public final  int     scancode;
    public final  int     action;
    public final  int     mod;

    public KeyEvent(final IWindow window, final int key, final int scancode, final int action, final int mod){
        this.window = window;
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mod = mod;
    }

    @Override
    public IWindow getSource() {
        return window;
    }
}
