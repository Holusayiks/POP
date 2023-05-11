package org.example;

public class Producer implements Runnable {
    private final int id;
    private final Storage storage;
    private final int productAmount;

    public Producer(int id, Storage storage, int productAmount) {
        this.id = id;
        this.storage = storage;
        this.productAmount = productAmount;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i < productAmount; i++){
                Thread.sleep(0);

                System.out.println("Producer #" + id + " see if the storage is full.");
                storage.acquireFull();
                System.out.println("Producer #" + id + " near the storage with the item #" + i + '.');
                storage.acquireAccess();
                System.out.println("Producer #" + id + " in the storage with the item #" + i + '.');

                storage.putItem("Item " + i);
                System.out.println("Producer #" + id + " put item #" + i + " into the storage.");

                System.out.println("Producer #" + id + " near the exit.");
                storage.releaseAccess();
                System.out.println("Producer #" + id + " left the storage.");

                storage.releaseEmpty();
                System.out.println("The storage is no longer empty thanks to producer #" + id + '.');
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
