package de.webtwob.the.base.game.base.server;

import de.webtwob.the.base.game.api.IGameLogic;
import de.webtwob.the.base.game.api.service.IBaseMod;
import de.webtwob.the.base.game.api.IGameInstance;
import de.webtwob.the.base.game.api.service.IMod;
import de.webtwob.the.base.game.base.TheBaseGameLogic;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class ServerGameInstance implements IGameInstance.IServerInstance {

    private final IBaseMod baseMode;

    public static ServerGameInstance createInstance(IBaseMod baseMode){
        return new ServerGameInstance(baseMode);
    }

    private ServerGameInstance(IBaseMod baseMode){
        this.baseMode = baseMode;
    }

    @Override
    public IMod getBaseMod() {
        return baseMode;
    }

    @Override
    public IGameLogic getGameLogic() {
        return new TheBaseGameLogic();
    }
}
