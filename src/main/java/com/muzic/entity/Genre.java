package com.muzic.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity(name = "genre")
@Table(name = "t_genre")
@Data
public class Genre extends SuperEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "genre")
    private Set<Track> tracks;

    public Genre(String name) {
        this.name = name;
    }
}
