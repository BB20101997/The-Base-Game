package de.webtwob.the.base.game.base.network.messages;

import de.webtwob.the.base.game.api.network.IMessage;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class CloseMessage implements IMessage {

    String reason = "Unspecified Reason";

    public CloseMessage reason(final String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public void endcode(final ByteBuf buf) {
        byte[] reasonBytes = reason.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(reasonBytes.length);

        buf.writeBytes(reasonBytes);
    }

    @Override
    public void decode(final ByteBuf buf) {
        var reasonBytes = new byte[buf.readInt()];
        buf.readBytes(reasonBytes);
        reason = new String(reasonBytes,StandardCharsets.UTF_8);
    }

    public String reason() {
        return reason;
    }
}
