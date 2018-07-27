package de.webtwob.the.base.game.api.interfaces;

import de.webtwob.the.base.game.api.gui.ICurrentContext;
import de.webtwob.the.base.game.api.gui.IWindow;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public interface IRenderer {

    void init(IWindow window, ICurrentContext context);

    void destroy(IWindow window);

    void render(IWindow window, final ICurrentContext context);

    void update(IWindow window, ICurrentContext context, final long delta);

}
