package de.webtwob.the.base.game.api.network;

import io.netty.buffer.ByteBuf;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public interface IMessage {

    void endcode(ByteBuf buf);

    void decode(ByteBuf buf);

}
