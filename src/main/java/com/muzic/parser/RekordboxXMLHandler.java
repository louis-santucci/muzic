package com.muzic.parser;

import com.muzic.entity.*;
import com.muzic.model.*;
import com.muzic.service.EntityService;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RekordboxXMLHandler extends DefaultHandler {

    private final EntityService entityService;

    public RekordboxXMLHandler(boolean persist, EntityService entityService) {
        this.persist = persist;
        this.entityService = entityService;
    }

    private final boolean persist;
    private RekordboxXML rekordboxXML;

    // XML Nodes
    private static final String DJ_PLAYLISTS = "DJ_PLAYLISTS";
    private static final String COLLECTION = "COLLECTION";
    private static final String TRACK = "TRACK";
    private static final String PLAYLISTS = "PLAYLISTS";
    private static final String NODE = "NODE";

    // Attributes
    private static final String KEY = "Key";
    private static final String TRACK_ID = "TrackID";
    private static final String NAME = "Name";
    private static final String ARTIST = "Artist";
    private static final String ALBUM = "Album";
    private static final String GENRE = "Genre";
    private static final String KIND = "Kind";
    private static final String SIZE = "Size";
    private static final String TOTAL_TIME = "TotalTime";
    private static final String YEAR = "Year";
    private static final String AVERAGE_BPM = "AverageBpm";
    private static final String BIT_RATE = "BitRate";
    private static final String SAMPLE_RATE = "SampleRate";
    private static final String TONALITY = "Tonality";
    private static final String TYPE = "Type";
    private static final String COUNT = "Count";
    private static final String ENTRIES = "Entries";

    // NodeStacks
    private PlaylistTreeNode currentPlaylistNode = null;
    private final Stack<FolderTreeNode> folderTreeNodeStack = new Stack<>();


    @Override
    public void startDocument() {
        rekordboxXML = new RekordboxXML();
        rekordboxXML.setCollection(new HashMap<>());
        rekordboxXML.setPlaylistTree(new PlaylistTree());
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case TRACK:
                int keyIndex = getAttributeIndex(attributes, "Key");
                if (keyIndex != -1) {
                    String key = attributes.getValue(keyIndex);
                    Track track;
                    try {
                        Long trackId = Long.valueOf(key);
                        track = this.rekordboxXML.getCollection().get(trackId);
                    } catch (NumberFormatException e) {
                        track = null;
                    }
                    if (track != null) {
                        currentPlaylistNode.getTrackList().add(track);
                    }
                } else {
                    Map<String, String> attributesMap = attributesToMap(attributes);
                    Track track = persistTrack(attributesMap, persist);
                    this.rekordboxXML.getCollection().put(track.getRekordboxId(), track);
                }
                break;
            case NODE:
                Map<String, String> attributesMap = attributesToMap(attributes);
                // Folder node
                if (Integer.parseInt(attributesMap.get(TYPE)) == 0) {
                    FolderTreeNode node = new FolderTreeNode(
                            attributesMap.get(NAME),
                            Integer.parseInt(attributesMap.get(COUNT)),
                            this.folderTreeNodeStack.isEmpty() ? null : this.folderTreeNodeStack.peek());
                    this.folderTreeNodeStack.push(node);
                }
                // else Playlist node
                else {
                    int entries = Integer.parseInt(attributesMap.get(ENTRIES));
                    String name = attributesMap.get(NAME);
                    this.currentPlaylistNode = new PlaylistTreeNode(name, entries, this.folderTreeNodeStack.peek());
                }
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case NODE:
                if (this.currentPlaylistNode != null) {
                    PlaylistTreeNode current = this.currentPlaylistNode;
                    this.folderTreeNodeStack.peek().getChildNodes().add(current);
                    this.currentPlaylistNode = null;
                } else {
                    FolderTreeNode currentFolder = this.folderTreeNodeStack.pop();
                    if (this.folderTreeNodeStack.isEmpty()) {
                        this.rekordboxXML.getPlaylistTree().setNode(currentFolder);
                    } else {
                        this.folderTreeNodeStack.peek().getChildNodes().add(currentFolder);
                    }
                }
                break;
        }
    }

    private static Integer getAttributeIndex(Attributes attributes, String attr) {
        int len = attributes.getLength();
        return IntStream.range(0, len).filter(i -> attributes.getLocalName(i).equalsIgnoreCase(attr)).findFirst().orElse(-1);
    }

    private static Map<String, String> attributesToMap(Attributes attributes) {
        Map<String, String> map;
        int len = attributes.getLength();
        map = IntStream.range(0, len).boxed().collect(Collectors.toMap(attributes::getLocalName, attributes::getValue, (a, b) -> b));
        return map;
    }

    private Artist[] parseArtistField(String artistField) {
        return Arrays.stream(artistField.split(" & ")).map(s -> Artist.builder().name(s).build()).toArray(Artist[]::new);
    }

    private Track persistTrack(Map<String, String> attributesMap, boolean persist) {
        Genre genre = persist ? this.entityService.getOrCreate(attributesMap.get(GENRE)) : new Genre(attributesMap.get(GENRE));
        Set<Artist> artists = persist ? this.entityService.getOrCreate(parseArtistField(attributesMap.get(ARTIST))) : Arrays.stream(parseArtistField(attributesMap.get(ARTIST))).collect(Collectors.toSet());
        Album album = persist ? this.entityService.getOrCreate(attributesMap.get(ALBUM), artists) : new Album(attributesMap.get(ALBUM));
        Track track = Track.builder()
                .name(attributesMap.get(NAME))
                .rekordboxId(Long.valueOf(attributesMap.get(TRACK_ID)))
                .artists(artists)
                .album(album)
                .size(Long.valueOf(attributesMap.get(SIZE)))
                .duration(Long.valueOf(attributesMap.get(TOTAL_TIME)))
                .genre(genre)
                .format(AudioFormat.valueOf(attributesMap.get(KIND).split(" ")[1]))
                .year(Integer.valueOf(attributesMap.get(YEAR)))
                .bpm(Double.valueOf(attributesMap.get(AVERAGE_BPM)))
                .bitRate(Long.valueOf(attributesMap.get(BIT_RATE)))
                .sampleRate(Long.valueOf(attributesMap.get(SAMPLE_RATE)))
                .tonality(attributesMap.get(TONALITY))
                .build();
        return persist ? entityService.saveTrack(track) : track;
    }

    public RekordboxXML getRekordboxXML() {
        return rekordboxXML;
    }
}
