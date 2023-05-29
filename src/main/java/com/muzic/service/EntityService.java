package com.muzic.service;

import com.muzic.entity.Album;
import com.muzic.entity.Artist;
import com.muzic.entity.Genre;
import com.muzic.entity.Track;
import com.muzic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class EntityService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;

    @Autowired
    public EntityService(AlbumRepository albumRepository,
                         ArtistRepository artistRepository,
                         GenreRepository genreRepository,
                         PlaylistRepository playlistRepository,
                         TrackRepository trackRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
    }

    public Genre getOrCreate(String name) {
        Optional<Genre> genre = this.genreRepository.findGenresByName(name).stream().findFirst();
        return genre.orElseGet(() -> this.genreRepository.save(new Genre(name)));
    }

    public Set<Artist> getOrCreate(Artist[] artists) {
        Set<Artist> resultSet = new HashSet<>();
        Arrays.stream(artists).forEach(artist -> {
            Optional<Artist> artistDb = this.artistRepository.findArtistsByName(artist.getName()).stream().findFirst();
            resultSet.add(artistDb.orElseGet(() -> this.artistRepository.save(new Artist(artist.getName()))));
        });
        return resultSet;
    }

    public Album getOrCreate(String albumName, Set<Artist> artists) {
        Optional<Album> album = this.albumRepository.findAlbumsByNameAndArtists(albumName, artists.toArray(new Artist[0])).stream().findFirst();
        return album.orElseGet(() -> this.albumRepository.save(Album.builder().name(albumName).artists(artists).build()));
    }

    public Track saveTrack(Track track) {
        return this.trackRepository.save(track);
    }
}
