package com.sum.kafka_wikimedia_producer.kafka;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class WikimediaProducer {

    private final WebClient wikimediaClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = "wikimedia-recent-changes-webflux";

    public void sendMessage() {
        Flux<String> stream = wikimediaClient.get()
                .uri("/recentchange")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class);
        
        stream.delayElements(Duration.ofSeconds(1))
                .subscribe(
                    event -> { 
                        log.info("Event: {}", event);
                        kafkaTemplate.send(topic, event);
                    },
                    error -> log.error("Error", error),
                    () -> log.info("Completed")
                );
    }
}
