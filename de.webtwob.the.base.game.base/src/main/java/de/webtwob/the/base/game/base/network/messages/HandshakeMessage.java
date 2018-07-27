package de.webtwob.the.base.game.base.network.messages;

import de.webtwob.the.base.game.api.network.IMessage;
import de.webtwob.the.base.game.api.util.ByteBufHelper;
import io.netty.buffer.ByteBuf;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class HandshakeMessage implements IMessage {

    private HandshakeType type = HandshakeType.START;
    private Object conetnt;

    public static class AuthInfo{
        String version = "0.0.0-pre";
        Object authInfo = null;
    }

    public enum HandshakeType{
        START,
        IDENTIFY {
            @Override
            public void endcode(final ByteBuf buf, Object content) {
                if(content instanceof AuthInfo){
                    ByteBufHelper.writeString(buf,((AuthInfo) content).version);
                }
            }

            @Override
            public Object decode(final ByteBuf buf) {
                AuthInfo authInfo = new AuthInfo();
                authInfo.version = ByteBufHelper.readString(buf);
                return authInfo;
            }
        };

        public void endcode(final ByteBuf buf, Object content){
        }

        public Object decode(final ByteBuf buf){
            return null;
        }
    }

    @Override
    public void endcode(final ByteBuf buf) {
        buf.writeInt(type.ordinal());
        type.endcode(buf,null);
    }

    @Override
    public void decode(final ByteBuf buf) {
        type = HandshakeType.values()[buf.readInt()];
        conetnt = type.decode(buf);
    }

    public HandshakeType getType() {
        return type;
    }
}
