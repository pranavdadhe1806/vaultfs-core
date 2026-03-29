package datastructures;

import java.util.ArrayList;

/**
 * Custom hash map with separate chaining for filename to metadata lookup.
 */
public class FileHashMap {
    /** Stores one key-value pair in a bucket chain. */
    private static class Entry {
        String key;
        models.FileMetadata value;
        Entry next;

        /** Creates an entry node with key and value. */
        Entry(String key, models.FileMetadata value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry[] buckets;
    private int capacity;
    private int size;

    /** Initializes the hash map with default capacity 16. */
    public FileHashMap() {
        this.capacity = 16;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }

    /** Computes the bucket index for a key. */
    private int hash(String key) {
        return Math.abs(key.hashCode() % capacity);
    }

    /** Inserts a new key-value pair or updates the existing key. */
    public void put(String key, models.FileMetadata value) {
        int index = hash(key);
        Entry head = buckets[index];

        if (head == null) {
            buckets[index] = new Entry(key, value);
            size++;
            return;
        }

        Entry current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }

            if (current.next == null) {
                break;
            }
            current = current.next;
        }

        current.next = new Entry(key, value);
        size++;
    }

    /** Returns the metadata value mapped to the key or null when absent. */
    public models.FileMetadata get(String key) {
        int index = hash(key);
        Entry current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /** Removes a key from the map and returns whether deletion occurred. */
    public boolean remove(String key) {
        int index = hash(key);
        Entry head = buckets[index];

        if (head == null) {
            return false;
        }

        if (head.key.equals(key)) {
            buckets[index] = head.next;
            size--;
            return true;
        }

        Entry current = head;
        while (current.next != null) {
            if (current.next.key.equals(key)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /** Returns true if the map contains the given key. */
    public boolean contains(String key) {
        return get(key) != null;
    }

    /** Returns all stored metadata values across all buckets. */
    public ArrayList<models.FileMetadata> getAll() {
        ArrayList<models.FileMetadata> all = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            Entry current = buckets[i];
            while (current != null) {
                all.add(current.value);
                current = current.next;
            }
        }
        return all;
    }

    /** Returns the number of entries in the map. */
    public int size() {
        return size;
    }

    /** Prints every key-value pair or an empty-map message. */
    public void display() {
        if (size == 0) {
            System.out.println("  (empty)");
            return;
        }

        for (int i = 0; i < capacity; i++) {
            Entry current = buckets[i];
            while (current != null) {
                System.out.println(current.key + " -> " + current.value.toString());
                current = current.next;
            }
        }
    }
}
