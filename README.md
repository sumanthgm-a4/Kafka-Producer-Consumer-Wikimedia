# Wikimedia Kafka Streaming Pipeline

A simple real-time streaming pipeline built with Spring Boot, Apache Kafka, WebFlux, and MySQL.

The producer streams live Wikimedia RecentChange events using Spring WebFlux and pushes them into Kafka.

The consumer reads those messages from Kafka and stores them into MySQL.

---

# Flow

```mermaid
flowchart LR
    A[Wikimedia RecentChange Stream] --> B[Spring Boot Producer - WebFlux]
    B --> C[(Kafka Broker)]
    C --> D[Spring Boot Consumer]
    D --> E[(MySQL)]
```

---

# Streaming Flow

```mermaid
sequenceDiagram
    participant Wikimedia
    participant Producer
    participant Kafka
    participant Consumer
    participant MySQL

    loop Continuous Stream
        Wikimedia->>Producer: Stream Event
        Producer->>Kafka: Publish Event
        Kafka->>Consumer: Consume Event
        Consumer->>MySQL: Store Event
    end
```

---

# Producer

The producer uses Spring WebFlux to continuously stream Wikimedia RecentChange events.

```mermaid
flowchart TD
    A[Open Wikimedia Stream]
    B[Receive Event]
    C[Send To Kafka]

    A --> B
    B --> C
    C --> B
```

---

# Consumer

The consumer continuously reads messages from Kafka and stores them into MySQL.

```mermaid
flowchart TD
    A[(Kafka Topic)] --> B[Consumer]
    B --> C[(MySQL)]
```

---

# Running Kafka Broker (No Zookeeper)

Using the latest Kafka KRaft mode image.

## Run Kafka Container

```bash
docker run -d \
  --name kafka \
  -p 9092:9092 \
  apache/kafka:latest
```

---

# Connect Into Kafka Container

```bash
docker exec -it kafka bash
```

---

# Kafka Commands

## Show Topics

```bash
/opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --list
```

---

## Consume Messages From Beginning

```bash
/opt/kafka/bin/kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic <topic> \
  --from-beginning
```

---

## Consume Only New Messages

```bash
/opt/kafka/bin/kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic <topic>
```

---

# Running Applications

## Start Producer

```bash
./gradlew bootRun
```

Run this inside the producer project.

---

## Start Consumer

```bash
./gradlew bootRun
```

Run this inside the consumer project.

---

# End-to-End Pipeline

```mermaid
flowchart LR
    A[Wikipedia Edits Happening Live]
    --> B[WebFlux Producer Streams Events]
    --> C[(Kafka)]
    --> D[Consumer Reads Messages]
    --> E[(MySQL Stores Events)]
```

---
