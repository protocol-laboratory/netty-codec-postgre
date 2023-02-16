package io.github.protocol.codec.postgre;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PostgreDecoder extends LengthFieldBasedFrameDecoder {
    public PostgreDecoder() {
        this(PostgreConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public PostgreDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        return frame;
    }

    protected PostgreStartupMessage decodeStartupMessage(ByteBuf frame) {
        int protocolMajorVersion = frame.readShort();
        int protocolMinorVersion = frame.readShort();
        Map<String, String> map = new HashMap<>();
        while (true) {
            String key = readCString(frame);
            if (key.isEmpty()) {
                break;
            }
            String value = readCString(frame);
            map.put(key, value);
        }
        return new PostgreStartupMessage(protocolMajorVersion, protocolMinorVersion, map);
    }

    private String readCString(ByteBuf frame) {
        int length = frame.bytesBefore((byte) 0);
        if (length == -1) {
            throw new IllegalArgumentException("No \\0 found");
        }
        CharSequence charSequence = frame.readCharSequence(length, StandardCharsets.UTF_8);
        frame.skipBytes(1);
        return charSequence.toString();
    }

}
