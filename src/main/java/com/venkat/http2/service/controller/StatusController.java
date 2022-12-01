package com.venkat.http2.service.controller;

import com.venkat.http2.service.kafka.consumer.StatusConsumer;
import com.venkat.http2.service.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController

public class StatusController {

    @Autowired
    StatusService statusService;

    StatusConsumer kafkaConsumer = new StatusConsumer();


    @GetMapping(path = "/status/{appCode}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public  Flux<String> status(@PathVariable String appCode) {
        System.out.println("/status/"+appCode);
        return kafkaConsumer.receive(appCode);
    }

    @GetMapping("/emit/{appCode}/{msg}")
    public void emit(@PathVariable String appCode, @PathVariable String msg) {
        System.out.println("/emit/"+appCode);
        kafkaConsumer.onNewMsg(appCode, msg);
    }


//    @GetMapping("/{appCode}")
//    public Flux<ServerSentEvent<String>> streamStatus(@ApiParam(value = "appCode", required = true)
//                                                          @PathVariable String appCode) {
//        System.out.println("+++ streamStatus() +++");
//        return Flux
//                .interval(Duration.ofSeconds(2))
//                .map(sequence -> ServerSentEvent.<String>builder()
//                        .data(statusService.getStatus(appCode))
//                        .comment("test comment")
//                        .event("test event")
//                        .id(appCode)
//                        .build());
//    }
}

