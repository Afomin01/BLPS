package com.example.blps.service.impl;

import com.example.blps.model.Tag;
import com.example.blps.model.TagCounter;
import com.example.blps.repository.TagCounterRepository;
import com.example.blps.repository.TagRepository;
import com.example.blps.service.ITagCounterService;
import lombok.var;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagCounterService implements ITagCounterService {
    private final TagRepository tagRepository;
    private final TagCounterRepository tagCounterRepository;
    private final Producer<String, String> tagUsageRequestProducer;

    public TagCounterService(final TagRepository tagRepository,
                             final TagCounterRepository tagCounterRepository,
                             final Producer<String, String> tagUsageRequestProducer) {
        this.tagRepository = tagRepository;
        this.tagCounterRepository = tagCounterRepository;
        this.tagUsageRequestProducer = tagUsageRequestProducer;
    }

    @Override
    public long getTagCounter(UUID id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No tag with id " + id));
        Optional<TagCounter> tagCounter = tagCounterRepository.findById(tag.getId());

        return tagCounter.map(TagCounter::getCounter).orElse(0L);
    }

    public void sendTagUsageRequest(String tagName) {
        int partition = tagName.substring(0, 1).toLowerCase().compareTo("m") < 0 ? 0 : 1;

        ProducerRecord<String, String> record = new ProducerRecord<>("tagUsageRequests", partition, tagName, tagName);

        tagUsageRequestProducer.send(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "tagUsageRequests", partitions = "0"))
    private void consumeTagUsageRequestsFromFirstPartition(@Payload List<String> tagNames) {
        consumeTagUsageRequests(tagNames);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "tagUsageRequests", partitions = "1"))
    private void consumeTagUsageRequestsFromSecondPartition(@Payload List<String> tagNames) {
        consumeTagUsageRequests(tagNames);
    }

    private void consumeTagUsageRequests(List<String> tagNames) {
        Map<String, Long> counters = new HashMap<>();
        for (String tagName : tagNames) {
            long count = counters.getOrDefault(tagName, 0L);
            counters.put(tagName, count + 1);
        }

        for (var entry : counters.entrySet()) {
            String tagName = entry.getKey();
            long count = entry.getValue();

            Tag tag = tagRepository.findByName(tagName).orElseThrow(RuntimeException::new);
            TagCounter tagCounter = tagCounterRepository.findById(tag.getId())
                    .orElseGet(() -> new TagCounter(tag.getId()));
            tagCounter.incrementBy(count);

            tagCounterRepository.save(tagCounter);
        }
    }
}
