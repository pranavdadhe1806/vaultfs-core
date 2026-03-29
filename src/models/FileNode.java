package models;

import datastructures.FileHashMap;
import datastructures.FileLinkedList;
import java.util.ArrayList;

/**
 * Represents a directory node in the tree structure.
 */
public class FileNode {
    public String name;
    public String absolutePath;
    public FileNode parent;
    public ArrayList<FileNode> children;
    public datastructures.FileLinkedList files;
    public datastructures.FileHashMap fileIndex;

    /**
     * Initializes the directory node fields.
     */
    public FileNode(String name, String absolutePath, FileNode parent) {
        this.name = name;
        this.absolutePath = absolutePath;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.files = new FileLinkedList();
        this.fileIndex = new FileHashMap();
    }

    /**
     * Adds a child directory to this node.
     */
    public void addChild(FileNode child) {
        children.add(child);
    }

    /**
     * Removes a child directory from this node.
     */
    public void removeChild(FileNode child) {
        children.remove(child);
    }

    /**
     * Returns the child directory with the given name if it exists.
     */
    public FileNode getChild(String name) {
        for (FileNode child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    /**
     * Returns the absolute path of this node.
     */
    public String getPath() {
        return absolutePath;
    }
}
