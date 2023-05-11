package org.example;

public class Main {
    public static void main(String[] args) {
        int storageSize = 12;
        int consumerAmount = 6;
        int consumerProductAmount = 2;
        int producerAmount = 4;
        int producerProductAmount = 3;

        Storage storage = new Storage(storageSize);

        Thread[] consumerThreads = new Thread[consumerAmount];
        for(int i = 0; i < consumerAmount; i++){
            consumerThreads[i] = new Thread(
                    new Consumer(i, storage, consumerProductAmount)
            );
        }

        Thread[] producerThreads = new Thread[producerAmount];
        for(int i = 0; i < producerAmount; i++){
            producerThreads[i] = new Thread(
                    new Producer(i, storage, producerProductAmount)
            );
        }

        for (Thread consumer : consumerThreads) {
            consumer.start();
        }
        for (Thread producer : producerThreads) {
            producer.start();
        }
    }
}