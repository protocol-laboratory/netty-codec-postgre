package io.github.protocol.codec.postgre;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@ChannelHandler.Sharable
public class PostgreEncoder extends MessageToMessageEncoder<PostgreMessage>  {

    public static final PostgreEncoder INSTANCE = new PostgreEncoder();

    @Override
    protected void encode(ChannelHandlerContext ctx, PostgreMessage msg, List<Object> out) throws Exception {
        out.add(doEncode(ctx, msg));
    }

    ByteBuf doEncode(ChannelHandlerContext ctx, PostgreMessage msg) {
        if (msg instanceof PostgreStartupMessage) {
            return encodeStartup(ctx, (PostgreStartupMessage) msg);
        } else {
            throw new IllegalArgumentException("Unknown message type: " + msg.getClass().getName());
        }
    }

    private ByteBuf encodeStartup(ChannelHandlerContext ctx, PostgreStartupMessage msg) {
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeShort(msg.protocolMajorVersion());
        buf.writeShort(msg.protocolMinorVersion());
        for (Map.Entry<String, String> entry : msg.parameters().entrySet()) {
            buf.writeBytes(entry.getKey().getBytes(StandardCharsets.UTF_8));
            buf.writeByte(0);
            buf.writeBytes(entry.getValue().getBytes(StandardCharsets.UTF_8));
            buf.writeByte(0);
        }
        buf.writeByte(0);
        return buf;
    }
}
