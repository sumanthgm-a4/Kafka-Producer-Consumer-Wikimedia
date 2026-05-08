package com.sum.kafka_wikimedia_producer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WikimediaWebClientConfig {

    @Bean
    public WebClient wikimediaClient() {
        return WebClient.builder()
                .baseUrl("https://stream.wikimedia.org/v2/stream")
                .build();
    }
}
