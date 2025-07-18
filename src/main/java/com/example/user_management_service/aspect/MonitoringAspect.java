package com.example.user_management_service.aspect;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

// Aspekt za prikupljanje metrika pomoću Micrometera.
// Primenjuje se na sve klase označene sa @Monitored i meri:
//  - trajanje izvršavanja poziva
//  - broji uspešne i neuspešne pozive

@Aspect
@Component
public class MonitoringAspect {
    private final MeterRegistry registry;

    public MonitoringAspect(MeterRegistry registry) {
        this.registry = registry;
    }

    @Around("@within(com.example.user_management_service.aspect.Monitored)"
            + " || @annotation(com.example.user_management_service.aspect.Monitored)")
    public Object aroundMonitored(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature sig        = (MethodSignature) pjp.getSignature();
        String          className  = sig.getDeclaringType().getSimpleName();
        String          methodName = sig.getName();
        Tags            baseTags   = Tags.of("class", className, "method", methodName);


        Timer.Sample sample = Timer.start(registry);

        try {
            Object result = pjp.proceed();

            // Uspešan poziv
            Counter.builder("service.method.invocations")
                    .tags(baseTags.and("status", "success"))
                    .description("Broj poziva servisa/metoda")
                    .register(registry)
                    .increment();

            sample.stop(
                    Timer.builder("service.method.latency")
                            .tags(baseTags.and("status", "success"))
                            .description("Vreme izvršenja servisa/metoda")
                            .publishPercentileHistogram(true)
                            .register(registry)
            );

            return result;
        } catch (Throwable ex) {
            // Neuspešan poziv
            Counter.builder("service.method.invocations")
                    .tags(baseTags.and("status", "failure"))
                    .description("Broj poziva servisa/metoda")
                    .register(registry)
                    .increment();

            sample.stop(
                    Timer.builder("service.method.latency")
                            .tags(baseTags.and("status", "failure"))
                            .description("Vreme izvršenja servisa/metoda")
                            .publishPercentileHistogram(true)
                            .register(registry)
            );

            throw ex;
        }
    }
}