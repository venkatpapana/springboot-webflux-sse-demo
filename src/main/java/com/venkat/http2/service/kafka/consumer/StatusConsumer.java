package com.venkat.http2.service.kafka.consumer;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatusConsumer {
    Map<String, Sinks.Many<String>> subscribers = new HashMap<>();

    public Flux<String> receive(String appCode) {
        final Sinks.Many<String> sseEventSender = Sinks.many().multicast().onBackpressureBuffer();
        subscribers.put(appCode, sseEventSender);
        return sseEventSender.asFlux();
    }

    public void consume() {
    }

    public void onNewMsg(String appCode, String msg) {
        if(subscribers.containsKey(appCode)) {
            subscribers.get(appCode).tryEmitNext((new Date()).toString() + " Status Update: " + msg);
        }
    }
}

