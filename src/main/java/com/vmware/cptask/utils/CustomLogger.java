package com.vmware.cptask.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class CustomLogger {
    public static void writeActionLogsToFile(String pair) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
            writer.write(pair);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeRemainingDataToFile(BlockingQueue<String> dataQueue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
            while (!dataQueue.isEmpty()) {
                String block = dataQueue.poll();
                String pair = String.format("<%s, %d>", block.substring(0, 4), block.length());
                writer.write(pair);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
