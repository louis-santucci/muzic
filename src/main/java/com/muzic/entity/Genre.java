package com.muzic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "genre")
@Table(name = "t_genre")
@NoArgsConstructor
@AllArgsConstructor
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
