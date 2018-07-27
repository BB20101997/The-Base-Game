package de.webtwob.the.base.game.api.gui;

import de.webtwob.the.base.game.api.util.SafeAutoCloseable;

/**
 * Created by BB20101997 on 21. Jul. 2018.
 */
public interface ICurrentContext
        extends SafeAutoCloseable {

    void setClearColor(Color c);

    void clear();

    void swapBuffer();

    void setSwapInterval(int interval);
}
