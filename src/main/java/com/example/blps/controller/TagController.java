package com.example.blps.controller;

import com.example.blps.dto.response.TagInfoResponse;
import com.example.blps.model.Tag;
import com.example.blps.service.ITagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(value = "/tag")
public class TagController {
    private final ITagService tagService;

    public TagController(final ITagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagInfoResponse>> getAllTags(@RequestParam(required = false, defaultValue = "") final String name) {
        List<Tag> tags;


        if (name.equals("")) {
            tags = tagService.getAllTags();
        } else {
            tags = tagService.getAllTagsByLikeName(name);
        }

        List<TagInfoResponse> response = tags.
                stream().
                map(e -> new TagInfoResponse(
                                e.getId(),
                                e.getName()
                        )
                ).
                collect(Collectors.toList());

        return ResponseEntity.
                ok().
                contentType(MediaType.APPLICATION_JSON).
                body(response);
    }
}
