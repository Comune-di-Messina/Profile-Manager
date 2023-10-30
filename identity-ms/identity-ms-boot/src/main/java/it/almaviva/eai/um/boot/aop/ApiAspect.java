package it.almaviva.eai.um.boot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ApiAspect {

  @Pointcut("within(it.almaviva.eai.um.api.*)")
  public void loggingPointcut() {}

  @Before("loggingPointcut()")
  public void logBefore(JoinPoint jointPoint){
    String event = jointPoint.getSignature().toShortString();
    log.debug(event);
  }

}
