package de.webtwob.the.base.game.api.interfaces;

import de.webtwob.the.base.game.api.util.RegistryID;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IRegistry <Type extends IRegistrable> extends IRegistrable {

    /**
     * @param  registered the object to be registered
     * **/
    void register(Type registered);

    Type getByID(RegistryID saveID);

    Class<Type> getRegistryTypeClass();

}
