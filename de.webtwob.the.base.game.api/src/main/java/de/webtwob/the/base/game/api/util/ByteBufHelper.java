package de.webtwob.the.base.game.api.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class ByteBufHelper {

    private ByteBufHelper(){}

    public static ByteBuf writeString(ByteBuf buf, String string){
        var ind = buf.duplicate();
        buf.writeInt(0);
        var size = buf.writeCharSequence(string, StandardCharsets.UTF_8);
        ind.writeInt(size);
        return buf;
    }

    public static String readString(ByteBuf buf){
        return String.valueOf(buf.readCharSequence(buf.readInt(), StandardCharsets.UTF_8));
    }

}
