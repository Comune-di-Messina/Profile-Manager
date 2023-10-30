package it.almaviva.eai.ljsa.sdk.core.throwable;

import lombok.Getter;

@Getter
public class LjsaVersionException extends RuntimeException {

    /** The name of the instance that was of the wrong type. */
    private final String oldVersion;

    /** The name of the instance that was of the trust type. */
    private final String currentVersion;


    /**
     * Create a new LjsaVersionException with the specified message.
     * @param msg the detail message
     */
    public LjsaVersionException(String msg, String currentVersion, String oldVersion) {
        super(msg);
        this.currentVersion = currentVersion;
        this.oldVersion = oldVersion;
    }

}
