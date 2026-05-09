package com.sum.kafka_wikimedia_consumer.repository;

import org.springframework.stereotype.Repository;

import com.sum.kafka_wikimedia_consumer.entity.WikimediaRecord;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WikimediaDataRepository extends JpaRepository<WikimediaRecord, Long> {

}
