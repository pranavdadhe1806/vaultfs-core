package datastructures;

import java.util.ArrayList;
import models.FileMetadata;

/**
 * Custom singly linked list for files inside one directory.
 * This class is the primary file-order data structure (no java.util.LinkedList).
 */
public class FileLinkedList {
    /**
     * Stores one file metadata element and a pointer to the next node.
     */
    private static class Node {
        FileMetadata data;
        Node next;

        /**
         * Creates a node for the linked list.
         */
        Node(FileMetadata data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    /**
     * Initializes an empty linked list.
     */
    public FileLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds a file metadata node to the end of the list.
     */
    public void add(FileMetadata file) {
        Node newNode = new Node(file);
        if (head == null) {
            head = newNode;
            size++;
            return;
        }

        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
        size++;
    }

    /**
     * Removes the first file node that matches the given filename.
     */
    public boolean remove(String filename) {
        if (head == null) {
            return false;
        }

        if (head.data.filename.equals(filename)) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.filename.equals(filename)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /**
     * Returns whether a file with the given filename exists.
     */
    public boolean contains(String filename) {
        Node current = head;
        while (current != null) {
            if (current.data.filename.equals(filename)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Returns file metadata for the given filename or null if absent.
     */
    public FileMetadata get(String filename) {
        Node current = head;
        while (current != null) {
            if (current.data.filename.equals(filename)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Returns all file metadata values in insertion order.
     */
    public ArrayList<FileMetadata> getAll() {
        ArrayList<FileMetadata> all = new ArrayList<>();
        Node current = head;
        while (current != null) {
            all.add(current.data);
            current = current.next;
        }
        return all;
    }

    /**
     * Returns the current number of file nodes.
     */
    public int size() {
        return size;
    }

    /**
     * Prints all file rows or an empty-list message.
     */
    public void display() {
        if (head == null) {
            System.out.println("  (no files)");
            return;
        }

        Node current = head;
        while (current != null) {
            System.out.println(current.data.toString());
            current = current.next;
        }
    }
}
