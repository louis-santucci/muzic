package com.muzic.repository;

import com.muzic.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    List<Artist> findArtistsByName(String name);
}
