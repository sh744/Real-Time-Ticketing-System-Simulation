package com.Ticket2.New.Model;

import com.Ticket2.New.Service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.Ticket2.New.Model.Configuration;

@Component
public class Vendor implements Runnable {
    private final TicketPoolService ticketPool;
    private final Configuration configuration;
    private int vendorId;
    private int ticketsReleased = 0;

    @Autowired
    public Vendor(TicketPoolService ticketPool, Configuration configuration) {
        this.ticketPool = ticketPool;
        this.configuration = configuration;
    }

    public void initialize(int vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        int ticketsPerRelease = configuration.getTicketReleaseRate();
        int releaseInterval = 1000; // 1 second

        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (int i = 0; i < ticketsPerRelease; i++) {
                    Ticket ticket = new Ticket(50.00);
                    ticketPool.addTicket(ticket);
                    ticketsReleased++;
                }

                Logger.logDebug("Vendor " + vendorId + " released " + ticketsPerRelease + " tickets.");
                Thread.sleep(releaseInterval);

            } catch (InterruptedException e) {
                Logger.logError("Vendor " + vendorId + " was interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int getTicketsReleased() {
        return ticketsReleased;
    }
}
