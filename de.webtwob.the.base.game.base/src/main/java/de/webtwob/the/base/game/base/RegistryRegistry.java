package de.webtwob.the.base.game.base;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.interfaces.IRegistrable;
import de.webtwob.the.base.game.api.interfaces.IRegistry;
import de.webtwob.the.base.game.api.util.RegistryID;
import de.webtwob.the.base.game.base.util.BasicRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class RegistryRegistry extends BasicRegistry<IRegistry> {

    private Map<Class<?>, IRegistry<?>> classRegistryMap = new HashMap<>();
    private IGameInstance               instance;

    public RegistryRegistry(IGameInstance instance) {
        super(IRegistry.class, RegistryID.createRegistryID(instance.getBaseMod().id(), "registryregistry"));
        this.instance = instance;
        register(this);
    }

    @Override
    public void register(final IRegistry registered) {
        //id or class already Registered
        if (classRegistryMap.containsKey(registered.getRegistryTypeClass()) ||
            registryContent.containsKey(registered.getRegistryID())) {
            return;
        }

        classRegistryMap.put(registered.getRegistryTypeClass(), registered);
        registryContent.put(registered.getRegistryID(), registered);
    }

    @SuppressWarnings("unchecked")
    public <T extends IRegistrable> IRegistry<T> getByClass(Class<T> clazz) {
        IRegistry<?> res      = classRegistryMap.get(clazz);
        var          regClazz = res.getRegistryTypeClass();
        if (clazz == regClazz) {
            return (IRegistry<T>) res;
        } else {
            return null;
        }
    }

}
