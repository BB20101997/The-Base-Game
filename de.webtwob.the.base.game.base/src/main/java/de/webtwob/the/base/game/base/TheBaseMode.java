package de.webtwob.the.base.game.base;

import de.webtwob.the.base.game.api.BaseMod;
import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.service.IBaseModFactory;
import de.webtwob.the.base.game.base.client.ClientGameInstance;
import de.webtwob.the.base.game.base.server.ServerGameInstance;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class TheBaseMode implements IBaseModFactory {

    private static final System.Logger LOGGER = System.getLogger(TheBaseMode.class.getName());

    private static final BaseMod INSTANCE = BaseMod.create()
                                                   .withID("de.webtwob.the.base.mod")
                                                   .withClientFunction(TheBaseMode::getClientInstance)
                                                   .withServerFunction(TheBaseMode::getServerInstance);

    private static IGameInstance.IClientInstance getClientInstance(BaseMod we) {
        LOGGER.log(System.Logger.Level.DEBUG, "Creating new Client Instance");
        return ClientGameInstance.createInstance(we);
    }

    public static IGameInstance.IServerInstance getServerInstance(BaseMod we) {
        LOGGER.log(System.Logger.Level.DEBUG, "Creating new Server Instance");
        return ServerGameInstance.createInstance(we);
    }

    @Override
    public BaseMod get() {

        return INSTANCE;
    }
}
