package com.example.blps.controller;

import com.example.blps.dto.response.TagGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/tag")
public class TagController {
    @GetMapping(value = "/{name}")
    public ResponseEntity<TagGetResponse> getAllTags(@PathVariable final String name){

        return null;
    }
}
