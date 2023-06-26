package com.vmware.cptask;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final int producerId;
    private final BlockingQueue<String> dataQueue;
    private final Random random;
    private final int queueCapacity;

    public Producer(int producerId, BlockingQueue<String> dataQueue, int queueCapacity) {
        this.producerId = producerId;
        this.dataQueue = dataQueue;
        this.random = new Random();
        this.queueCapacity = queueCapacity;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int sleepDuration = random.nextInt(101);
                Thread.sleep(sleepDuration);

                int blockSize = random.nextInt(19000001) + 2000000;
                StringBuilder block = new StringBuilder(blockSize);
                for (int i = 0; i < blockSize; i++) {
                    char randomChar = (char) (random.nextInt(26) + 'a');
                    block.append(randomChar);
                }

                synchronized (dataQueue) {
                    while (dataQueue.size() >= queueCapacity) {
                        dataQueue.wait(); // Wait until the queue has available space
                    }
                    dataQueue.add(block.toString());
                    if (dataQueue.size() <= 80) {
                        dataQueue.notifyAll(); // Notify waiting consumers
                    }
                }
                System.out.printf("Producer %d generated a block of size %d after sleeping for %d milliseconds. Queue size: %d%n", producerId, blockSize, sleepDuration, dataQueue.size());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
