package com.muzic.model;

import com.muzic.entity.Playlist;

import java.util.ArrayList;
import java.util.List;

public class FolderTreeNode extends TreeNode {
    private static final String SEPARATOR = "/";
    private List<TreeNode> childNodes;
    public FolderTreeNode(String name, int count, TreeNode parentNode) {
        super(name, count, parentNode);
        this.childNodes = new ArrayList<>(count);
    }

    public List<TreeNode> getChildNodes() {
        return childNodes;
    }

    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        String nodePath = this.getNodePath();
        childNodes.forEach(treeNode -> {
            if (treeNode instanceof PlaylistTreeNode) {
                PlaylistTreeNode node = (PlaylistTreeNode) treeNode;
                Playlist playlist = Playlist.builder()
                        .name(node.getName())
                        .trackList(node.getTrackList())
                        .path(nodePath + SEPARATOR + node.getName())
                        .build();
                playlists.add(playlist);
            } else {
                FolderTreeNode node = (FolderTreeNode) treeNode;
                playlists.addAll(node.getAllPlaylists());
            }
        });
        return playlists;
    }
}
