package de.webtwob.the.base.game.base.client;

import de.webtwob.the.base.game.api.BaseMod;
import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.interfaces.IRegistrable;
import de.webtwob.the.base.game.api.interfaces.IRegistry;
import de.webtwob.the.base.game.api.interfaces.IRenderer;
import de.webtwob.the.base.game.api.network.INetworkHandler;
import de.webtwob.the.base.game.base.RegistryRegistry;
import de.webtwob.the.base.game.base.network.MessageHandlerRegistry;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class ClientGameInstance implements IGameInstance.IClientInstance {

    private final BaseMod                               baseMode;
    private final IRenderer                             renderer;
    private final RegistryRegistry                      registryRegistry;
    private final INetworkHandler.IClientNetworkHandler networkHandler;

    private ClientGameInstance(final BaseMod baseMode) {
        this.baseMode = baseMode;
        renderer = new TheBaseGameRenderer();
        registryRegistry = new RegistryRegistry(this);
        registryRegistry.register(new MessageHandlerRegistry(this));
        networkHandler = new ClientNetworkHandler(null, 201, this);
    }

    public static ClientGameInstance createInstance(BaseMod baseMode) {
        return new ClientGameInstance(baseMode);
    }

    @Override
    public BaseMod getBaseMod() {
        return baseMode;
    }

    @Override
    public <TYPE extends IRegistrable> IRegistry<TYPE> getRegistry(final Class<TYPE> clazz) {
        return registryRegistry.getByClass(clazz);
    }

    @Override
    public INetworkHandler.IClientNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    @Override
    public IRenderer getRenderer() {
        return renderer;
    }
}
