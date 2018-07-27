package de.webtwob.the.base.game.base.util;

import de.webtwob.the.base.game.api.interfaces.IRegistrable;
import de.webtwob.the.base.game.api.interfaces.IRegistry;
import de.webtwob.the.base.game.api.util.RegistryID;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class BasicRegistry <TYPE extends IRegistrable> implements IRegistry<TYPE> {

    private final Class<TYPE> clazz;
    private final RegistryID  id;
    protected Map<RegistryID, TYPE> registryContent = new HashMap<>();

    public BasicRegistry(final Class<TYPE> clazz, final RegistryID id) {
        this.clazz = clazz;
        this.id = id;
    }

    @Override
    public void register(final TYPE registered) {
        registryContent.put(registered.getRegistryID(), registered);
    }

    @Override
    public TYPE getByID(final RegistryID saveID) {
        return registryContent.get(saveID);
    }

    @Override
    public Class<TYPE> getRegistryTypeClass() {
        return clazz;
    }

    @Override
    public RegistryID getRegistryID() {
        return id;
    }
}
