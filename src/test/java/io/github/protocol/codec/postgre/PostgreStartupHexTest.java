package io.github.protocol.codec.postgre;

import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PostgreStartupHexTest {

    private final PostgreDecoder decoder = new PostgreDecoder();

    @Test
    public void case1() {
        PostgreStartupMessage message = decoder.decodeStartupMessage(Unpooled.wrappedBuffer(TestData.STARTUP_CASE1));
        Assertions.assertEquals(3, message.protocolMajorVersion());
        Assertions.assertEquals(0, message.protocolMinorVersion());
        Assertions.assertEquals(6, message.parameters().size());
    }
}
