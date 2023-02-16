package io.github.protocol.codec.postgre;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

public class PostgreStartupCodecTest {

    private final PostgreDecoder decoder = new PostgreDecoder();

    @Test
    public void case1() {
        PostgreStartupMessage message = new PostgreStartupMessage(3, 0, new HashMap<>());
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = PostgreEncoder.INSTANCE.doEncode(ctx, message);
        PostgreStartupMessage startupMessage = decoder.decodeStartupMessage(byteBuf);
        Assertions.assertEquals(3, startupMessage.protocolMajorVersion());
        Assertions.assertEquals(0, startupMessage.protocolMinorVersion());
        Assertions.assertEquals(0, startupMessage.parameters().size());
    }
}
