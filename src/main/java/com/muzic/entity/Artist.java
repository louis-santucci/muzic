package com.muzic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "artist")
@Table(name = "t_artist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist extends SuperEntity {
    @Column(name = "name")
    private String name;
    @ManyToMany
    @Column(name = "tracks")
    private Set<Track> tracks;
    @ManyToMany
    @Column(name = "albums")
    private Set<Album> albums;

    public Artist(String name) {
        this.name = name;
    }
}
