package de.webtwob.the.base.game.base.network;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.network.IMessage;
import de.webtwob.the.base.game.api.network.IMessageHandler;
import de.webtwob.the.base.game.api.util.ByteBufHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class MessageEncoder extends MessageToByteEncoder<IMessage> {

    private IGameInstance instance;

    public MessageEncoder(final IGameInstance instance) {
        this.instance = instance;
    }

    @Override
    protected void encode(final ChannelHandlerContext ctx, final IMessage msg, final ByteBuf out) {
        ByteBuf buf = Unpooled.buffer();
        var regId = ((MessageHandlerRegistry)instance.getRegistry(IMessageHandler.class)).getMessageHandler(msg.getClass()).getRegistryID();
        ByteBufHelper.writeString(out,regId.toString());
        msg.endcode(buf);
        out.writeBytes(buf);
    }
}
