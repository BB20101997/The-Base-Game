package de.webtwob.the.base.game.api.event;

import de.webtwob.the.base.game.api.interfaces.IEvent;
import de.webtwob.the.base.game.api.interfaces.IRegistrable;
import de.webtwob.the.base.game.api.interfaces.IRegistry;

/**
 * Created by BB20101997 on 18. Jul. 2018.
 */
public class RegistrationEvent<T extends IRegistrable> implements IEvent<IRegistry<T>> {

    private final IRegistry<T> registry;

    public RegistrationEvent(IRegistry<T> registry){
        this.registry = registry;
    }

    @Override
    public IRegistry<T> getSource() {
        return registry;
    }
}
