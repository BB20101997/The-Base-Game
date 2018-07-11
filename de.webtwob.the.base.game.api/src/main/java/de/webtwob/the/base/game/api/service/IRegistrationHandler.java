package de.webtwob.the.base.game.api.service;

import de.webtwob.the.base.game.api.IRegistry;
import de.webtwob.the.base.game.api.IRegistrateable;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IRegistrationHandler<Type extends IRegistrateable> {

    void handleRegistration(IRegistry<Type> registry);

    Class<Type> getTypeClass();

}
