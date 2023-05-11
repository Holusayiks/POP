package org.example;

public class Consumer implements Runnable {
    private final int id;
    private final Storage storage;
    private final int productAmount;

    public Consumer(int id, Storage storage, int productAmount) {
        this.id = id;
        this.storage = storage;
        this.productAmount = productAmount;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i < productAmount; i++){
                Thread.sleep(0);

                System.out.println("Consumer #" + id + " check if the storage is empty.");
                storage.acquireEmpty();

                System.out.println("Consumer #" + id + " near the storage for item #" + i + '.');
                storage.acquireAccess();
                System.out.println("Consumer #" + id + " in the storage for item #" + i + '.');
                System.out.println("Consumer #" + id + " take " + storage.takeItem() + '.');
                System.out.println("Consumer #" + id + " near the exit.");
                storage.releaseAccess();
                System.out.println("Consumer #" + id + " left the storage.");

                storage.releaseFull();
                System.out.println("The storage is no longer full thanks to consumer #" + id + '.');
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
