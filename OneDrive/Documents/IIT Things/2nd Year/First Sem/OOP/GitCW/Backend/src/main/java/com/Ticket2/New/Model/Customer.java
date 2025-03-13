package com.Ticket2.New.Model;

import com.Ticket2.New.Service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.Ticket2.New.Model.Configuration;

import java.util.List;
import java.util.Random;

@Component
public class Customer implements Runnable{
    private final TicketPoolService ticketPool;
    private final Configuration configuration;
    private int customerID;
    private int ticketsBought = 0;

    @Autowired
    public Customer(TicketPoolService ticketPool, Configuration configuration) {
        this.ticketPool = ticketPool;
        this.configuration = configuration;
    }

    public void initialize(int customerID) {
        this.customerID = customerID;
    }

    @Override
    public void run() {
        Random rand = new Random();
        int customerRetrievalRate = configuration.getCustomerRetrievalRate();
        while(!Thread.interrupted()) {
            try{
                int ticketAmount = rand.nextInt(3) + 1;
                List<Ticket> purchasedTickets = ticketPool.getTicket(ticketAmount);
                if (purchasedTickets.isEmpty()) {
                    Logger.logInfo("Customer " + customerID + " could not buy tickets.");
                }
                else {
                    for (Ticket ticket : purchasedTickets) {
                        Logger.logDebug("Customer " + customerID + " bought Ticket " + ticket.getTicketID());
                        ticketsBought += purchasedTickets.size();
                    }
                }
                Thread.sleep(customerRetrievalRate);

            } catch (InterruptedException e) {
                Logger.logError("Customer ID: " + customerID + " Interrupted");
                break;
            }
        }
    }

    public int getTicketsBought() {
        return ticketsBought;
    }
}
