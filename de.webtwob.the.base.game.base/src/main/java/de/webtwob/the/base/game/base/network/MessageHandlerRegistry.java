package de.webtwob.the.base.game.base.network;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.network.IMessage;
import de.webtwob.the.base.game.api.network.IMessageHandler;
import de.webtwob.the.base.game.api.util.RegistryID;
import de.webtwob.the.base.game.base.util.BasicRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class MessageHandlerRegistry extends BasicRegistry<IMessageHandler> {

    private Map<Class<? extends IMessage>, IMessageHandler<?>> messageHandlerMap = new HashMap<>();

    public MessageHandlerRegistry(IGameInstance instance) {
        super(IMessageHandler.class, RegistryID.createRegistryID(instance.getBaseMod().id(), "messageregistry"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void register(final IMessageHandler registered) {
        var clazz = registered.getMessageType();
        if (registryContent.containsKey(registered.getRegistryID()) || messageHandlerMap.containsKey(clazz)) {
            //message already has a handler or handler id already in use
            return;
        }
        registryContent.put(registered.getRegistryID(), registered);
        messageHandlerMap.put(registered.getMessageType(), registered);
    }

    @SuppressWarnings("unchecked")
    public <T extends IMessage> IMessageHandler<T> getMessageHandler(Class<T> clazz) {
        return (IMessageHandler<T>) messageHandlerMap.get(clazz);
    }
}
