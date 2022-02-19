package com.example.blps.service.impl;

import com.example.blps.dto.TagCreateDTO;
import com.example.blps.model.Tag;
import com.example.blps.repository.TagRepository;
import com.example.blps.service.ITagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagService implements ITagService {
    private final TagRepository tagRepository;

    public TagService(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return null;
    }

    @Override
    public List<Tag> getAllTagsByName(String name) {
        return null;
    }

    @Override
    public Tag createTag(TagCreateDTO tagCreateDTO) {
        return null;
    }
}
