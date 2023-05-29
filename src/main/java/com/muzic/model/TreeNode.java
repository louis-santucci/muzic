package com.muzic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TreeNode {

    private static final String SEPARATOR = "/";

    private String name;
    private int count;
    private TreeNode parentNode;

    public String getNodePath() {
        if (this.parentNode != null) {
            return this.parentNode.getNodePath() + SEPARATOR + this.name;
        }
        return this.name;
    }
}
