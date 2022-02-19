package com.example.blps.dto.response;

import com.example.blps.model.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@AllArgsConstructor
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class TagInfoResponse {
    private final UUID id;
    private final String name;

    public TagInfoResponse(Tag tag) {
        id = tag.getId();
        name = tag.getName();
    }
}
