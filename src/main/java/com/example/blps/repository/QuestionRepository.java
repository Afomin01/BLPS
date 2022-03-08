package com.example.blps.repository;

import com.example.blps.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query(value = "select q from Question q where q.needs_moderation = true limit 1", nativeQuery = true)
    Optional<Question> getAnyUnmoderatedQuestion();

}