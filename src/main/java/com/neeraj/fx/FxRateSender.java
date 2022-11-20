package com.neeraj.fx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Component
public class FxRateSender {

    @Autowired
    private FxRateReceiver receiver;

    @PostConstruct
    public void onInit() {
        AtomicLong id = new AtomicLong();
        Flux.fromStream(Stream.generate(
                        () -> {
                            String ccyPair = Currency.random() + "/" + Currency.random();
                            return String.join(", ", String.valueOf(id.incrementAndGet()),
                                    ccyPair,
                                    BigDecimal.valueOf(new Random().nextDouble()).setScale(4, RoundingMode.HALF_EVEN).toString(),
                                    BigDecimal.valueOf(new Random().nextDouble()).setScale(4, RoundingMode.HALF_EVEN).toString(),
                                    LocalDateTime.now().format(Transformer.DATE_TIME_FORMATTER), "\n");
                        })).delayElements(Duration.ofSeconds(1))
                .subscribe(this::send);
    }

    public void send(String message) {
        receiver.onMessage(message);
    }

    private enum Currency {
        USD, GBP, EUR, JPY, CHF, AUD, CAD, CNY, NZD, INR, BZR, SEK, ZAR, HKD;

        public static Currency random() {
            return values()[new Random().nextInt(values().length)];
        }
    }

}
