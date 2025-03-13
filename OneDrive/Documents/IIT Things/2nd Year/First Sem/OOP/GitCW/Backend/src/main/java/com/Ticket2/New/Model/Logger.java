package com.Ticket2.New.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "simulation.log";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        String logMessage = LocalDateTime.now().format(DATE_TIME_FORMATTER) + " - " + message;
        System.out.println(logMessage); // Print to console
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) { // Changed to true
            writer.println(logMessage);
            writer.flush(); // Ensure it's written immediately
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }



    public static void logError(String message) {
        log("Error - " + message);
    }

    public static void logInfo(String message) {
        log(message);
    }

    public static void logDebug(String message) {
        log(message);
    }
}
