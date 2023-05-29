package com.muzic.repository;

import com.muzic.entity.Album;
import com.muzic.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {
    List<Album> findAlbumsByNameAndArtists(String name, Artist[] artists);
}
