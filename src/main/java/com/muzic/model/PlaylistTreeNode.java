package com.muzic.model;

import com.muzic.entity.Track;

import java.util.ArrayList;
import java.util.List;

public class PlaylistTreeNode extends TreeNode {

    private List<Track> trackList;

    public PlaylistTreeNode(String name, int count, TreeNode parentNode) {
        super(name, count, parentNode);
        this.trackList = new ArrayList<>(count);
    }

    public List<Track> getTrackList() {
        return trackList;
    }
}
