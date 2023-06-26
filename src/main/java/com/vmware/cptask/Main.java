package com.vmware.cptask;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of producers (N): ");
        int numProducers = scanner.nextInt();

        System.out.print("Enter the number of consumers (M): ");
        int numConsumers = scanner.nextInt();

        int queueCapacity = 100;

        ProducerManager producerManager = new ProducerManager(numProducers, numConsumers, queueCapacity);
        producerManager.startProducers();
        producerManager.startConsumers();

        startQueueSizeMonitoring(producerManager);

        scanner.nextLine();
        System.out.print("Press Enter to stop the application...");
        scanner.nextLine();

        producerManager.stopProducers();
        producerManager.waitForConsumers();

        scanner.close();
    }

    private static void startQueueSizeMonitoring(ProducerManager producerManager) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            int queueSize = producerManager.getDataQueueSize();
            System.err.println("Current queue size: " + queueSize);
        }, 0, 1, TimeUnit.SECONDS);
    }
}
