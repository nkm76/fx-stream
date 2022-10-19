package com.neeraj.fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
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
        Flux<String> messageFlux = Flux.create(sink -> receiver.messageConsumer(sink::next));
        Flux<FxRate> fxRateFlux = messageFlux.map(Transformer::toFxRates).flatMap(Flux::fromIterable);
        fxRateFlux.subscribe(fxRate -> cache.put(fxRate.ccyPair(), fxRate));
//        fxRateFlux.subscribe(fxRate -> logger.info(fxRate.toString()));

        // Seed data used for testing UI
        Flux.just("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n" +
                        "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002",
                "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002",
                "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100",
                "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110"
        ).subscribe(receiver::onMessage);
    }
}
