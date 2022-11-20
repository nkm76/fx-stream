package com.neeraj.fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@Component
public class FxRateApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(FxRateApplicationInitializer.class);
    @Autowired
    private FxRateReceiver receiver;
    @Autowired
    private FxRateCache cache;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        Flux<String> messageFlux = Flux.create(sink -> receiver.messageProcessor(sink::next));
        Flux<FxRate> fxRateFlux = messageFlux.map(Transformer::toFxRates).flatMap(Flux::fromIterable);
        ConnectableFlux<FxRate> publisher = fxRateFlux.publish();
        publisher.connect();
        publisher.subscribe(fxRate -> logger.info(fxRate.toString()));
        publisher.subscribe(fxRate -> cache.put(fxRate.ccyPair(), fxRate));
    }
}
