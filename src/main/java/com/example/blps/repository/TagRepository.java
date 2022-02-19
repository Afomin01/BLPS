package com.example.blps.repository;

import com.example.blps.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByName(String name);

    @Query("select t from Tag t where upper(t.name) like upper(concat('%', ?1, '%'))")
    List<Tag> findByNameContains(String name);

}