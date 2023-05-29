package com.muzic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

    public Artist(String name) {
        this.name = name;
    }
}
