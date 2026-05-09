package com.sum.kafka_wikimedia_consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sum.kafka_wikimedia_consumer.entity.WikimediaRecord;
import com.sum.kafka_wikimedia_consumer.repository.WikimediaDataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaDatabaseConsumer {

    private final WikimediaDataRepository repository;

    @KafkaListener(
        topics = {"wikimedia-recent-changes-webflux"},
        groupId = "consumer-group"
    )
    public void consume(String message) {
        // log.info("Event Message -> {}", message);

        WikimediaRecord record = new WikimediaRecord();
        record.setWikiEventData(message);
        repository.save(record);
    }
}
