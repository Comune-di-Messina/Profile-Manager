package it.almaviva.eai.ljsa.sdk.core.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartupLjsa implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        String version = EnableLjsa.class.getPackage().getImplementationVersion();
        log.info("LJSA Version : "+version);
    }
}
