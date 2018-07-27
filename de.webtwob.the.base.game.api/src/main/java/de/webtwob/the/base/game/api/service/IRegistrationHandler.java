package de.webtwob.the.base.game.api.service;

import de.webtwob.the.base.game.api.event.RegistrationEvent;
import de.webtwob.the.base.game.api.interfaces.function.IEventHandler;
import de.webtwob.the.base.game.api.interfaces.IRegistry;
import de.webtwob.the.base.game.api.interfaces.IRegistrable;
import de.webtwob.the.base.game.api.interfaces.function.IModAssociated;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IRegistrationHandler<TYPE extends IRegistrable> extends IEventHandler<IRegistry<TYPE>,RegistrationEvent<TYPE>> , IModAssociated {

    void handleRegistration(IRegistry<TYPE> registry);

    Class<TYPE> getTypeClass();

}
