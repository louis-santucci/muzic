package com.muzic.model;

import com.muzic.entity.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaylistTree {
    FolderTreeNode node;

    List<Playlist> getAllPlaylists() {
        List<Playlist> playlistList = new ArrayList<>();
        return node.getAllPlaylists();
    }
}
