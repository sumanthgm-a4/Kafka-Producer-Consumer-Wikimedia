package com.sum.kafka_wikimedia_producer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sum.kafka_wikimedia_producer.kafka.WikimediaProducer;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class KafkaWikimediaProducerApplication implements CommandLineRunner {

	private final WikimediaProducer wikimediaProducer;

	public static void main(String[] args) {
		SpringApplication.run(KafkaWikimediaProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		wikimediaProducer.sendMessage();
	}

}
