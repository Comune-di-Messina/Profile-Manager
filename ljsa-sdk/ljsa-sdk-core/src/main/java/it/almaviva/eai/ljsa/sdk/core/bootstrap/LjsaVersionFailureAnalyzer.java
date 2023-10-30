package it.almaviva.eai.ljsa.sdk.core.bootstrap;

import it.almaviva.eai.ljsa.sdk.core.throwable.LjsaVersionException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class LjsaVersionFailureAnalyzer extends AbstractFailureAnalyzer<LjsaVersionException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, LjsaVersionException cause) {
        return new FailureAnalysis(getDescription(cause), getAction(cause), cause);
    }

    private String getDescription(LjsaVersionException ex) {
        return String.format("Application could not be started because your Ljsa version is %s",
                ex.getOldVersion());
    }

    private String getAction(LjsaVersionException ex) {
        return String.format("Consider updating LJSA version to %s",
                ex.getCurrentVersion());
    }

}
