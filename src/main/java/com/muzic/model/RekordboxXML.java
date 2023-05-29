package com.muzic.model;

import com.muzic.entity.Track;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class RekordboxXML {
    private Map<Long, Track> collection;
    private PlaylistTree playlistTree;
}
