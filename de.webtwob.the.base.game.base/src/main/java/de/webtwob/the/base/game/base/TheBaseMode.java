package de.webtwob.the.base.game.base;

import de.webtwob.the.base.game.api.IGameInstance;
import de.webtwob.the.base.game.api.service.IBaseMod;
import de.webtwob.the.base.game.base.client.ClientGameInstance;
import de.webtwob.the.base.game.base.server.ServerGameInstance;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class TheBaseMode implements IBaseMod {

    @Override
    public String getModId() {
        return "de.webtwob.the.base.mode";
    }

    @Override
    public IGameInstance.IClientInstance getClientInstance() {
        return ClientGameInstance.createInstance(this);
    }

    @Override
    public IGameInstance.IServerInstance getServerInstance() {
        return ServerGameInstance.createInstance(this);
    }
}
