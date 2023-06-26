package com.vmware.cptask;

import com.vmware.cptask.utils.CustomLogger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducerManager {
    private final int numProducers;
    private final int numConsumers;
    private final BlockingQueue<String> dataQueue;
    private final ExecutorService producerExecutor;
    private final ExecutorService consumerExecutor;
    private boolean producersStopped;

    public ProducerManager(int numProducers, int numConsumers, int queueCapacity) {
        this.numProducers = numProducers;
        this.numConsumers = numConsumers;
        this.dataQueue = new LinkedBlockingQueue<>(queueCapacity);
        this.producerExecutor = Executors.newFixedThreadPool(numProducers);
        this.consumerExecutor = Executors.newFixedThreadPool(numConsumers);
        this.producersStopped = false;
    }

    public void startProducers() {
        for (int i = 0; i < numProducers; i++) {
            producerExecutor.execute(new Producer(i + 1, dataQueue, dataQueue.remainingCapacity()));
        }
        producerExecutor.shutdown();
        System.out.println("All producers have been submitted");
    }

    public void stopProducers() {
        if (!producersStopped) {
            producerExecutor.shutdownNow();
            producersStopped = true;
            System.out.println("Producers have been stopped");
        }
    }

    public void startConsumers() {
        for (int i = 0; i < numConsumers; i++) {
            consumerExecutor.execute(new Consumer(i + 1, dataQueue));
        }
        consumerExecutor.shutdown();
        System.out.println("All consumers have been submitted");
    }

    public void waitForConsumers() {
        try {
            consumerExecutor.shutdown();
            if (!consumerExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                System.err.println("Timeout occurred while waiting for consumers to finish");
            } else {
                System.out.println("All consumers have finished");
            }

            CustomLogger.writeRemainingDataToFile(dataQueue);

            producerExecutor.shutdown();
            if (!producerExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                System.err.println("Timeout occurred while waiting for producers to finish");
            } else {
                System.out.println("All producers have finished");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while waiting for executor termination: " + e.getMessage());
        }
    }


    public int getDataQueueSize() {
        return dataQueue.size();
    }
}
