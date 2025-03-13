package com.Ticket2.New.Service;

import com.Ticket2.New.Model.Configuration;
import com.Ticket2.New.Model.Customer;
import com.Ticket2.New.Model.Logger;
import com.Ticket2.New.Model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class SimulationService {
    public static int totalTicketsBought = 0;
    public static int totalTicketsReleased = 0;
    private final TicketPoolService ticketPoolService;
    private final Configuration configuration;
    private final Thread[] vendorThreads = new Thread[2];
    private final Thread[] customerThreads = new Thread[5];
    private final Vendor[] vendors = new Vendor[2];
    private final Customer[] customers = new Customer[5];
    boolean isRunning = false;
    private StringBuilder terminalOutput = new StringBuilder();
    private static String lastLogMessage = "";

    @Autowired
    public SimulationService(TicketPoolService ticketPoolService, Configuration configuration) {
        this.ticketPoolService = ticketPoolService;
        this.configuration = configuration;
    }

    public synchronized String startSimulation() {
        if (isRunning) {
            Logger.logError("Simulation is already running");
            return "Simulation is already running.";
        }
        isRunning = true;

        Logger.logInfo("Simulation Started");

        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new Vendor(ticketPoolService, configuration);
            vendors[i].initialize(i+1);
            vendorThreads[i] = new Thread(vendors[i]);
            vendorThreads[i].start();
        }

        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(ticketPoolService,configuration);
            customers[i].initialize(i+1);
            customerThreads[i] = new Thread(customers[i]);
            customerThreads[i].start();
        }
        return "Simulation Started.";
    }
    public synchronized String stopSimulation() {
        if (!isRunning) {
            Logger.logError("Simulation is already stopped");
            return "Simulation is already stopped.";
        }
        isRunning = false;
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        try {
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }
            for (Thread customerThread : customerThreads) {
                customerThread.join();
            }
        } catch (InterruptedException e) {
            Logger.logError("Error stopping threads.");
        }
        Logger.logInfo("Simulation Stopped");
        return "Simulation stopped.";
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getTotalTicketsBought() {
        for (Customer customer : customers) {
            totalTicketsBought += customer.getTicketsBought();
        }
        return totalTicketsBought;
    }

    public int getTotalTicketsReleased() {
        for (Vendor vendor : vendors) {
            totalTicketsReleased += vendor.getTicketsReleased();
        }
        return totalTicketsReleased;
    }

    public void log(String message) {
        terminalOutput.append(message).append("\n");
        Logger.log(message);
    }

    public String getTerminalOutput() {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("simulation.log"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.compareTo(lastLogMessage) > 0) {
                    output.append(line).append("\n");
                }
            }
            if (output.length() > 0) {
                lastLogMessage = output.toString().split("\n")[output.toString().split("\n").length - 1];
            }
        } catch (IOException e) {
            Logger.logError("Error reading log file: " + e.getMessage());
        }
        return output.toString();
    }
}
