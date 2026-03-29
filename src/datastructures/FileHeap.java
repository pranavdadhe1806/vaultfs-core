package datastructures;

import java.util.ArrayList;

/**
 * Custom max heap for tracking largest files by size.
 */
public class FileHeap {
    /** Represents one file entry stored in the heap. */
    public static class HeapEntry {
        public String filename;
        public String absolutePath;
        public long sizeBytes;

        /** Creates a heap entry with filename, path, and size. */
        public HeapEntry(String filename, String absolutePath, long sizeBytes) {
            this.filename = filename;
            this.absolutePath = absolutePath;
            this.sizeBytes = sizeBytes;
        }
    }

    private HeapEntry[] heap;
    private int size;
    private int capacity;

    /** Initializes the max heap with default capacity 1000. */
    public FileHeap() {
        this.capacity = 1000;
        this.heap = new HeapEntry[capacity];
        this.size = 0;
    }

    /** Inserts a file entry and restores max-heap order upward. */
    public void insert(String filename, String absolutePath, long sizeBytes) {
        if (size >= capacity) {
            return;
        }

        heap[size] = new HeapEntry(filename, absolutePath, sizeBytes);
        size++;
        heapifyUp(size - 1);
    }

    /** Removes and returns the maximum entry from the heap. */
    public HeapEntry extractMax() {
        if (size == 0) {
            return null;
        }

        HeapEntry result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        if (size > 0) {
            heapifyDown(0);
        }
        return result;
    }

    /** Restores max-heap order by bubbling an index upward. */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].sizeBytes > heap[parent].sizeBytes) {
                swap(index, parent);
            } else {
                break;
            }
            index = parent;
        }
    }

    /** Restores max-heap order by bubbling an index downward. */
    private void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < size && heap[left].sizeBytes > heap[largest].sizeBytes) {
                largest = left;
            }
            if (right < size && heap[right].sizeBytes > heap[largest].sizeBytes) {
                largest = right;
            }

            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    /** Returns up to k largest entries without modifying the original heap. */
    public ArrayList<HeapEntry> topK(int k) {
        ArrayList<HeapEntry> result = new ArrayList<>();
        if (k <= 0 || size == 0) {
            return result;
        }

        FileHeap copy = new FileHeap();
        for (int i = 0; i < size; i++) {
            HeapEntry e = heap[i];
            copy.insert(e.filename, e.absolutePath, e.sizeBytes);
        }

        int limit = k;
        if (limit > size) {
            limit = size;
        }

        for (int i = 0; i < limit; i++) {
            HeapEntry max = copy.extractMax();
            if (max == null) {
                break;
            }
            result.add(max);
        }
        return result;
    }

    /** Removes an entry by absolute path and rebalances the heap. */
    public boolean remove(String absolutePath) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (heap[i].absolutePath.equals(absolutePath)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false;
        }

        heap[index] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        if (index < size) {
            heapifyUp(index);
            heapifyDown(index);
        }
        return true;
    }

    /** Updates entry size by path and restores heap order. */
    public void update(String absolutePath, long newSize) {
        for (int i = 0; i < size; i++) {
            if (heap[i].absolutePath.equals(absolutePath)) {
                heap[i].sizeBytes = newSize;
                heapifyUp(i);
                heapifyDown(i);
                return;
            }
        }
    }

    /** Returns the number of entries currently in the heap. */
    public int size() {
        return size;
    }

    /** Prints ranked heap entries using B/KB/MB formatted size values. */
    public void display() {
        if (size == 0) {
            System.out.println("  (no files in heap)");
            return;
        }

        for (int i = 0; i < size; i++) {
            HeapEntry entry = heap[i];
            System.out.println((i + 1) + ". " + entry.filename + " — " + formatSize(entry.sizeBytes));
        }
    }

    /** Swaps two heap entries by index. */
    private void swap(int i, int j) {
        HeapEntry temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /** Formats raw bytes into B, KB, or MB text. */
    private String formatSize(long sizeBytes) {
        if (sizeBytes < 1024) {
            return sizeBytes + " B";
        }
        if (sizeBytes < 1_048_576) {
            return String.format("%.1f KB", sizeBytes / 1024.0);
        }
        return String.format("%.1f MB", sizeBytes / 1_048_576.0);
    }
}
