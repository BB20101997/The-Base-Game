package de.webtwob.the.base.game.base;

import de.webtwob.the.base.game.api.IGameInstance;
import de.webtwob.the.base.game.api.service.IMod;
import de.webtwob.the.base.game.api.IRegistrateable;
import de.webtwob.the.base.game.api.IRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class RegistryRegistry implements IRegistry<IRegistry> {

    private Map<Class<?>,IRegistry<?>> registryMap       = new HashMap<>();
    private Map<String, IRegistry<?>>  stringRegistryMap = new HashMap<>();
    private IGameInstance              instance;

    public RegistryRegistry(IGameInstance instance){
        this.instance = instance;
    }

    @Override
    public synchronized void register(final IRegistry registered) {
        if(registryMap.containsKey(registered.getRegistryTypeClass())){
            return; //already Registered Type
        }
        if(stringRegistryMap.containsKey(registered.getID())){
            return; //id already Registered
        }

        registryMap.put(registered.getRegistryTypeClass(),registered);
        stringRegistryMap.put(registered.getID(),registered);
    }

    @Override
    public IRegistry getByID(final String saveID) {
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends IRegistrateable> IRegistry<T> getByClass(Class<T> clazz){
        IRegistry<?> res = registryMap.get(clazz);
        var regClazz = res.getRegistryTypeClass();
        if(clazz==regClazz){
            return (IRegistry<T>) res;
        }else{
            return null;
        }
    }

    @Override
    public Class<IRegistry> getRegistryTypeClass() {
        return IRegistry.class;
    }

    @Override
    public String getID() {
        return "";
    }

    @Override
    public IMod getContainingMod() {
        return instance.getBaseMod();
    }

}
