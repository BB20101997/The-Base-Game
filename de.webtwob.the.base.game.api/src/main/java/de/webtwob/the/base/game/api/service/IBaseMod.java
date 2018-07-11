package de.webtwob.the.base.game.api.service;

import de.webtwob.the.base.game.api.IGameInstance;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public interface IBaseMod extends IMod {

    @Override
    default Collection<String> getRequiredModIDs() {
        return Collections.emptyList();
    }

    IGameInstance.IClientInstance getClientInstance();

    IGameInstance.IServerInstance getServerInstance();


}
