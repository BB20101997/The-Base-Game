package de.webtwob.the.base.game.api;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IRegistry <Type extends IRegistrateable> extends IRegistrateable {

    /**
     * @param  registered the object to be registered
     * **/
    void register(Type registered);

    Type getByID(String saveID);

    Class<Type> getRegistryTypeClass();

}
