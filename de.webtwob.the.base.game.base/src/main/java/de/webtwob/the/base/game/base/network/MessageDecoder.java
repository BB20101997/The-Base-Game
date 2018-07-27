package de.webtwob.the.base.game.base.network;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class MessageDecoder extends ChannelInboundHandlerAdapter {

    private IGameInstance instance;

    public MessageDecoder(IGameInstance instance){

        this.instance = instance;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
