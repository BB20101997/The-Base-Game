package de.webtwob.the.base.game.api.network;

import de.webtwob.the.base.game.api.interfaces.IRegistrable;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public interface IMessageHandler<T extends IMessage> extends IRegistrable {

    T createMessageInstance();

    void handleMessage(ChannelHandlerContext ctx, T message);

    Class<T> getMessageType();
}
