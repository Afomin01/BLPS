package com.example.blps.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagCounter {
    @Id
    private UUID tagId;

    private long counter;

    public TagCounter(UUID tadId) {
        this.tagId = tadId;
        this.counter = 0;
    }

    public void incrementBy(long value) {
        counter += value;
    }
}
