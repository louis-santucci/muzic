package com.muzic.entity;

import com.muzic.model.AudioFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity(name = "track")
@Table(name = "t_track")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Track extends SuperEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "rekordbox_id")
    private Long rekordboxId;
    @ManyToMany
    @Column(name = "artists")
    private Set<Artist> artists;
    @ManyToOne
    @JoinColumn(name = "album")
    private Album album;
    @ManyToMany
    @Column(name = "playlists")
    private List<Playlist> playlists;
    @Column(name = "size")
    private Long size;
    @Column(name = "duration")
    private Long duration;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @Column(name = "format")
    @Enumerated(EnumType.STRING)
    private AudioFormat format;
    @Column(name = "year")
    private Integer year;
    @Column(name = "bpm")
    private Double bpm;
    @Column(name = "tonality")
    private String tonality;
    @Column(name = "bit_rate")
    private Long bitRate;
    @Column(name = "sample_rate")
    private Long sampleRate;
}
