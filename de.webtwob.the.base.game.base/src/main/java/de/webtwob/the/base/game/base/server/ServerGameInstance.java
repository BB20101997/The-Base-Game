package de.webtwob.the.base.game.base.server;

import de.webtwob.the.base.game.api.BaseMod;
import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.interfaces.IGameLogic;
import de.webtwob.the.base.game.api.interfaces.IRegistrable;
import de.webtwob.the.base.game.api.interfaces.IRegistry;
import de.webtwob.the.base.game.api.network.INetworkHandler;
import de.webtwob.the.base.game.base.RegistryRegistry;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class ServerGameInstance implements IGameInstance.IServerInstance {

    private final BaseMod                               baseMode;
    private final RegistryRegistry                      registryRegistry = new RegistryRegistry(this);
    private final INetworkHandler.IServerNetworkHandler networkHandler;

    public static ServerGameInstance createInstance(BaseMod baseMode){
        return new ServerGameInstance(baseMode);
    }

    private ServerGameInstance(BaseMod baseMode){
        this.baseMode = baseMode;
        registryRegistry.register(new RegistryRegistry(this));
        networkHandler = new ServerNetworkHandler(null,201,this);
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
    public INetworkHandler.IServerNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    @Override
    public IGameLogic getGameLogic() {
        return new TheBaseGameLogic();
    }
}
