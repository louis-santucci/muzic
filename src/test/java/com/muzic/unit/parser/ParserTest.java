package com.muzic.unit.parser;

import com.muzic.entity.*;
import com.muzic.model.*;
import com.muzic.parser.XmlParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ParserTest {
    @Autowired
    private XmlParser parser;

    @Test
    void test1() {
        File file = new File("E:\\Documents\\Projects\\muzic\\src\\test\\resources\\test_xml\\test.xml");
        RekordboxXML xml = this.parser.parseXmlFile(file, false);
        Track track = Track.builder()
                .rekordboxId(10875444L)
                .name("Killa (Original Mix)")
                .artists(Collections.singleton(new Artist("Chané")))
                .album(new Album("Special Dancefloor Forces III"))
                .genre(new Genre("Techno"))
                .format(AudioFormat.AIFF)
                .year(2021)
                .duration(423L)
                .size(74818426L)
                .bpm(145.0)
                .bitRate(1411L)
                .sampleRate(44100L)
                .tonality("4A")
                .build();
        Track track2 = Track.builder()
                .rekordboxId(68536184L)
                .name("Die Prophezeiung (Hadone Version)")
                .artists(Arrays.stream(new Artist[]{new Artist("SHDW"), new Artist("Obscure Shape")}).collect(Collectors.toSet()))
                .album(new Album("Versionen 008"))
                .genre(new Genre("Techno"))
                .format(AudioFormat.AIFF)
                .year(2021)
                .duration(383L)
                .size(68394964L)
                .bpm(144.0)
                .bitRate(1411L)
                .sampleRate(44100L)
                .tonality("5A")
                .build();
        assertEquals(track, xml.getCollection().get(10875444L));
        assertEquals(track2, xml.getCollection().get(68536184L));

    }

    @Test
    public void test2_playlist() {
        File file = new File("E:\\Documents\\Projects\\muzic\\src\\test\\resources\\test_xml\\test.xml");
        RekordboxXML xml = this.parser.parseXmlFile(file, false);
        PlaylistTree tree = xml.getPlaylistTree();
        FolderTreeNode rootNode = tree.getNode();

        assertEquals("ROOT", rootNode.getName());

        assertEquals(4, rootNode.getCount());
        List<TreeNode> childNodes = rootNode.getChildNodes();
        assertEquals(4, childNodes.size());

        PlaylistTreeNode firstChild = (PlaylistTreeNode) childNodes.get(0);
        assertEquals("Louis", firstChild.getName());
        List<Track> firstList = firstChild.getTrackList();
        assertEquals(2, firstList.size());
        assertEquals(10875444L, firstList.get(0).getRekordboxId());
        assertEquals(68536184L, firstList.get(1).getRekordboxId());

        PlaylistTreeNode secondChild = (PlaylistTreeNode) childNodes.get(1);
        assertEquals("Louis", secondChild.getName());
        List<Track> secondList = secondChild.getTrackList();
        assertEquals(2, secondList.size());
        assertEquals(10875444L, secondList.get(0).getRekordboxId());
        assertEquals(68536184L, secondList.get(1).getRekordboxId());

        FolderTreeNode thirdChild = (FolderTreeNode) childNodes.get(2);
        assertEquals("Folder1", thirdChild.getName());
        assertEquals(0, thirdChild.getCount());

        FolderTreeNode lastChild = (FolderTreeNode) childNodes.get(3);
        PlaylistTreeNode lastNode = (PlaylistTreeNode) lastChild.getChildNodes().get(0);
        assertEquals("Louis", lastNode.getName());
        List<Track> lastList = lastNode.getTrackList();
        assertEquals(2, lastList.size());
        assertEquals(10875444L, lastList.get(0).getRekordboxId());
        assertEquals(68536184L, lastList.get(1).getRekordboxId());
    }

    @Test
    public void test3_playlist() {
        File file = new File("E:\\Documents\\Projects\\muzic\\src\\test\\resources\\test_xml\\test.xml");
        RekordboxXML xml = this.parser.parseXmlFile(file, false);
        PlaylistTree tree = xml.getPlaylistTree();

        List<Playlist> result = tree.getNode().getAllPlaylists();

        Track track = Track.builder()
                .rekordboxId(10875444L)
                .name("Killa (Original Mix)")
                .artists(Collections.singleton(new Artist("Chané")))
                .album(Album.builder().name("Special Dancefloor Forces III").build())
                .genre(new Genre("Techno"))
                .format(AudioFormat.AIFF)
                .year(2021)
                .duration(423L)
                .size(74818426L)
                .bpm(145.0)
                .bitRate(1411L)
                .sampleRate(44100L)
                .tonality("4A")
                .build();
        Track track2 = Track.builder()
                .rekordboxId(68536184L)
                .name("Die Prophezeiung (Hadone Version)")
                .artists(Arrays.stream(new Artist[]{new Artist("SHDW"), new Artist("Obscure Shape")}).collect(Collectors.toSet()))
                .album(new Album("Versionen 008"))
                .genre(new Genre("Techno"))
                .format(AudioFormat.AIFF)
                .year(2021)
                .duration(383L)
                .size(68394964L)
                .bpm(144.0)
                .bitRate(1411L)
                .sampleRate(44100L)
                .tonality("5A")
                .build();

        List<Track> tracks = new ArrayList<>();
        tracks.add(track);
        tracks.add(track2);

        Playlist playlist1 = Playlist.builder()
                .name("Louis")
                .trackList(tracks)
                .path("ROOT/Louis")
                .build();
        Playlist playlist2 = Playlist.builder()
                .name("Louis")
                .trackList(tracks)
                .path("ROOT/Louis")
                .build();
        Playlist playlist3 = Playlist.builder()
                .name("Louis")
                .trackList(tracks)
                .path("ROOT/Folder2/Louis")
                .build();
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(playlist1);
        playlists.add(playlist2);
        playlists.add(playlist3);

        assertEquals(playlists.size(), result.size());

        IntStream.range(0, playlists.size()).forEachOrdered(i -> assertEquals(playlists.get(i), result.get(i)));
    }
}
