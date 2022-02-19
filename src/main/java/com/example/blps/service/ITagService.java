package com.example.blps.service;

import com.example.blps.dto.TagCreateDTO;
import com.example.blps.model.Tag;

import java.util.List;

public interface ITagService {
    List<Tag> getAllTags();

    List<Tag> getAllTagsByLikeName(String name);

    Tag createTag(TagCreateDTO tagCreateDTO);
}
