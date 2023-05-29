package com.muzic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "album")
@Table(name = "t_album")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Album extends SuperEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "album")
    private Set<Track> tracks;

    @ManyToMany
    private Set<Artist> artists;

    public Album(String name) {
        this.name = name;
    }
}
