package com.example.blps.repository;

import com.example.blps.model.TagCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagCounterRepository extends JpaRepository<TagCounter, UUID> {
}
