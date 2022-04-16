package com.example.blps.repository;

import com.example.blps.model.UserQuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserQuestionVoteRepository extends JpaRepository<UserQuestionVote, UUID> {
    @Query("select (count(u) > 0) from UserQuestionVote u where u.question.id = ?1 and u.user.id = ?2")
    boolean doesVoteForQuestionByUserExists(UUID questionId, UUID userId);

    @Query("select u from UserQuestionVote u where u.question.id = ?1 and u.user.id = ?2")
    Optional<UserQuestionVote> findByQuestionIdAndUserId(UUID questionId, UUID userId);
}