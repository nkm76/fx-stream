package com.neeraj.fx;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * This component receives messages from Message source.
 */
@Component
public class FxRateReceiver {

    private Consumer<String> messageProcessor;

    public void messageProcessor(Consumer<String> messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public void onMessage(String message) {
        messageProcessor.accept(message);
    }

}
