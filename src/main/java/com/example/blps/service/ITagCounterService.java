package com.example.blps.service;

import java.util.UUID;

public interface ITagCounterService {
    long getTagCounter(UUID id);

    void sendTagUsageRequest(String tagName);
}
