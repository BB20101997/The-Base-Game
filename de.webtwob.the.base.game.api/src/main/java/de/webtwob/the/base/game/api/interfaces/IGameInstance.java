package de.webtwob.the.base.game.api.interfaces;

import de.webtwob.the.base.game.api.BaseMod;
import de.webtwob.the.base.game.api.network.INetworkHandler;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IGameInstance {

    BaseMod getBaseMod();

     <TYPE extends IRegistrable> IRegistry<TYPE> getRegistry(Class<TYPE> clazz);

    INetworkHandler getNetworkHandler();

    interface IClientInstance extends IGameInstance{

        IRenderer getRenderer();

        @Override
        INetworkHandler.IClientNetworkHandler getNetworkHandler();
    }

    interface IServerInstance extends IGameInstance{

        IGameLogic getGameLogic();

        @Override
        INetworkHandler.IServerNetworkHandler getNetworkHandler();
    }

}
