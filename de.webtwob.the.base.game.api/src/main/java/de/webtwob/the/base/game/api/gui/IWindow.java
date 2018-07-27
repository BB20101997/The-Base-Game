package de.webtwob.the.base.game.api.gui;

import de.webtwob.the.base.game.api.event.KeyEvent;
import de.webtwob.the.base.game.api.interfaces.IRenderer;
import de.webtwob.the.base.game.api.interfaces.function.IEventHandler;
import de.webtwob.the.base.game.api.internal.GLFW_Window;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by BB20101997 on 21. Jul. 2018.
 */
public interface IWindow {

    static IWindow createWindow(String title){
        return GLFW_Window.createWindow(title);
    }

    int width();

    int height();

    void setShouldClose(boolean shouldClose);

    Color getBackgroundColor();

    void setBackgroundColor(Color backgroundColor);

    ICurrentContext makeContextCurrent();

    void addKeyListener(IEventHandler<IWindow, KeyEvent> keyEventIEventHandler);

    void runWithMainThreadContext(Runnable run);

    void runWithMainThreadContext(Consumer<IMainThreadContext> mainThreadRunner);

    boolean runWithMainThreadContext(Predicate<IMainThreadContext> mainThreadContextPredicate);

    void removeAllListeners();

    void setMainRenderer(IRenderer renderer);
}
