package io.github.protocol.codec.postgre;

import java.util.Map;

public class PostgreStartupMessage extends PostgreMessage {

    private final int protocolMajorVersion;

    private final int protocolMinorVersion;

    private final Map<String, String> parameters;

    public PostgreStartupMessage(int protocolMajorVersion, int protocolMinorVersion, Map<String, String> parameters) {
        this.protocolMajorVersion = protocolMajorVersion;
        this.protocolMinorVersion = protocolMinorVersion;
        this.parameters = parameters;
    }

    public int protocolMajorVersion() {
        return protocolMajorVersion;
    }

    public int protocolMinorVersion() {
        return protocolMinorVersion;
    }

    public Map<String, String> parameters() {
        return parameters;
    }

}
