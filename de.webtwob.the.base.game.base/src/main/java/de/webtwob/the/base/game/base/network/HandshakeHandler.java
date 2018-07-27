package de.webtwob.the.base.game.base.network;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.util.Either;
import de.webtwob.the.base.game.base.network.messages.HandshakeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class HandshakeHandler extends ChannelInboundHandlerAdapter {

    private Either<IGameInstance.IClientInstance, IGameInstance.IServerInstance> instance;

    public HandshakeHandler(final Either<IGameInstance.IClientInstance, IGameInstance.IServerInstance> instance) {

    this.instance = instance;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        instance.apply(c->clientChannelRead(c,ctx,msg),s->serverChannelRead(s,ctx,msg));
    }

    private void serverChannelRead(final IGameInstance.IServerInstance serverInstance, final ChannelHandlerContext ctx, final Object msg) {
        if(msg instanceof HandshakeMessage){
            var hello = (HandshakeMessage) msg;
            switch (hello.getType()){

            }
        }
    }

    private void clientChannelRead(final IGameInstance.IClientInstance clientInstance, final ChannelHandlerContext ctx, final Object msg){

    }

}
