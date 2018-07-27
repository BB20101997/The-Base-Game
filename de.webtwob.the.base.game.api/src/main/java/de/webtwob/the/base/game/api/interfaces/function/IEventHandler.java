package de.webtwob.the.base.game.api.interfaces.function;

import de.webtwob.the.base.game.api.interfaces.IEvent;

/**
 * Created by BB20101997 on 18. Jul. 2018.
 */
@FunctionalInterface
public interface IEventHandler<S,E extends IEvent<S>> {

    void handleEvent(E event);

}
