package com.vmware.cptask;

import com.vmware.cptask.utils.CustomLogger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private final int consumerId;
    private final BlockingQueue<String> dataQueue;

    public Consumer(int consumerId, BlockingQueue<String> dataQueue) {
        this.consumerId = consumerId;
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String block = dataQueue.poll(100, TimeUnit.MILLISECONDS); // Wait for up to 100 milliseconds for an item

                if (block != null) {
                    String pair = "<" + block.substring(0, 4) + ", " + block.length() + ">";
                    CustomLogger.writeActionLogsToFile(pair);

                    System.out.printf("Consumer %d consumed a block of size %d. Queue size: %d%n", consumerId, block.length(), dataQueue.size());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}
