package com.muzic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "playlist")
@Table(name = "t_playlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Playlist extends SuperEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @ManyToMany
    @Column(name = "tracks")
    private List<Track> trackList;
}
