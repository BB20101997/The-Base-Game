package de.webtwob.the.base.game.api;

import de.webtwob.the.base.game.api.service.IMod;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IGameInstance {

    IMod getBaseMod();

    interface IClientInstance extends IGameInstance{

        IRenderer getRenderer();

    }

    interface IServerInstance extends IGameInstance{

        IGameLogic getGameLogic();

    }

}
