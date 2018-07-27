package de.webtwob.the.base.game.base.network;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.util.Either;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class GameMessageHandler extends ChannelInboundHandlerAdapter {

    public GameMessageHandler(final Either<IGameInstance.IClientInstance, IGameInstance.IServerInstance> instance) {

    }


}
