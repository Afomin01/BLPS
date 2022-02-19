package com.example.blps.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String text;
    private Instant creationTimeUTC;
    private int rating;
    private boolean needsModeration;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags;

    public Question(String title, String text, Instant creationTimeUTC, int rating, User user, Set<Tag> tags, boolean needsModeration) {
        this.title = title;
        this.text = text;
        this.creationTimeUTC = creationTimeUTC;
        this.rating = rating;
        this.user = user;
        this.tags = tags;
        this.needsModeration = needsModeration;
    }
}
