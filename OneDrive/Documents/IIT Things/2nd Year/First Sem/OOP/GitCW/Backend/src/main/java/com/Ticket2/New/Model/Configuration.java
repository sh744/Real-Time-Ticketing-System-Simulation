package com.Ticket2.New.Model;

import org.springframework.stereotype.Component;

@Component
public class Configuration {
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Constructors
    public Configuration() {
        this.maxTicketCapacity = 10;
    }

    // Getters and Setters
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate * 1000; // Convert to milliseconds
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "maxTicketCapacity=" + maxTicketCapacity +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", ticketReleaseRate=" + ticketReleaseRate +
                '}';
    }
}
