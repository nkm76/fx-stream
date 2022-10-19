package com.neeraj.fx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class FxRateRestController {
    @Autowired
    private FxRateCache fxRateCache;

    @GetMapping("/fxRates")
    public Flux<FxRate> fxRates() {
        return Flux.fromIterable(fxRateCache.values()).map(CommissionCalculator::applyCommissions);
    }

    @GetMapping("/fxRate/{baseCcy}/{quoteCcy}")
    public Mono<FxRate> fxRate(@PathVariable String baseCcy, @PathVariable String quoteCcy) {
        Optional<FxRate> fxRate = fxRateCache.get(baseCcy + "/" + quoteCcy);
        return Mono.justOrEmpty(fxRate).map(CommissionCalculator::applyCommissions);
    }

}