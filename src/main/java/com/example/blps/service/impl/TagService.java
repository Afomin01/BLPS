package com.example.blps.service.impl;

import com.example.blps.dto.TagCreateDTO;
import com.example.blps.model.Tag;
import com.example.blps.repository.TagRepository;
import com.example.blps.service.ITagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TagService implements ITagService {
    private final TagRepository tagRepository;

    public TagService(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getAllTagsByLikeName(String name) {
        return tagRepository.findByNameContains(name);
    }

    @Override
    public Tag createTag(TagCreateDTO tagCreateDTO) {
        Tag tag = new Tag(tagCreateDTO.getName());
        tag = tagRepository.save(tag);

        return tag;
    }

    public Optional<Tag> getTagByName(String name) {
        return tagRepository.findByName(name);
    }
}
