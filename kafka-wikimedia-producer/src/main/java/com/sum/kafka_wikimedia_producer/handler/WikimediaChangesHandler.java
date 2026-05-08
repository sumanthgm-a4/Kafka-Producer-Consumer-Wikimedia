package com.sum.kafka_wikimedia_producer.handler;

import org.springframework.kafka.core.KafkaTemplate;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class WikimediaChangesHandler implements BackgroundEventHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    @Override
    public void onClosed() throws Exception {
        log.info("SSE connection closed");
    }

    @Override
    public void onComment(String comment) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onError(Throwable t) {
        log.error("SSE error", t);
    }

    @Override
    // onMessage() is called whenever there's a new message (in Wikimedia, here)
    public void onMessage(String event, MessageEvent messageEvent) throws Exception {
        log.info("Event Data -> {}", messageEvent.getData());

        kafkaTemplate.send(topic, messageEvent.getData());
    }

    @Override
    public void onOpen() throws Exception {
        log.info("SSE connection opened");
    }

}
