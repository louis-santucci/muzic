package com.muzic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "album")
@Table(name = "t_album")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Album extends SuperEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "album")
    private Set<Track> tracks;

    public Album(String name) {
        this.name = name;
    }
}
