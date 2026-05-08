package com.sum.kafka_wikimedia_producer.kafka;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import com.sum.kafka_wikimedia_producer.handler.WikimediaChangesHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WikimediaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage() {
        String topic = "wikimedia-recent-changes";

        BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);

        ConnectStrategy strategy = ConnectStrategy.http(
                URI.create("https://stream.wikimedia.org/v2/stream/recentchange")
        )
        .header("Accept", "text/event-stream")
        .header(
            "User-Agent",
            "MySpringApp/1.0 (https://example.com; contact@example.com)"
        )
        .header("Cache-Control", "no-cache");

        EventSource.Builder builder =
                new EventSource.Builder(strategy);

        BackgroundEventSource source =
                new BackgroundEventSource.Builder(
                        eventHandler,
                        builder
                ).build();

        source.start();
    } 
}
