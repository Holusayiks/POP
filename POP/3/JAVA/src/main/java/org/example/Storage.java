package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Storage {
    private final Semaphore access;
    private final Semaphore empty;
    private final Semaphore full;
    private final List<String> storage;

    public Storage(int storageSize) {
        this.full = new Semaphore(storageSize);
        this.empty = new Semaphore(0);
        this.access = new Semaphore(1);
        this.storage = new ArrayList<>();
    }

    public String takeItem() {
        String item = storage.get(0);
        storage.remove(0);
        return item;
    }

    public void putItem(String item) {
        storage.add(item);
    }

    public void acquireAccess() {
        try {
            access.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void releaseAccess() {
        access.release();
    }

    public void acquireFull() {
        try {
            full.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void releaseFull() {
        full.release();
    }

    public void acquireEmpty() {
        try{
            empty.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void releaseEmpty() {
        empty.release();
    }
}
