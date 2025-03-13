package com.Ticket2.New.Service;

import com.Ticket2.New.Model.Configuration;
import com.Ticket2.New.Model.Logger;
import com.Ticket2.New.Model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TicketPoolService {
    private ConcurrentLinkedQueue <Ticket> ticketQueue;
    private int maxTicketCapacity;

    @Autowired
    public TicketPoolService(Configuration configuration) {
        ticketQueue = new ConcurrentLinkedQueue<>();
        this.maxTicketCapacity = configuration.getMaxTicketCapacity();
    }

    public synchronized void addTicket(Ticket ticket){
        while (ticketQueue.size() >= maxTicketCapacity) {
            try {
                Logger.logInfo("Ticket buffer is full!");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        this.ticketQueue.add(ticket);
        notifyAll();
    }
    public synchronized List<Ticket> getTicket(int ticketCount){
        List<Ticket> tickets = new ArrayList<>();
        while (ticketQueue.size() < ticketCount) {
            try{
                Logger.logInfo("Waiting for the ticket to be available.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return tickets;
            }
        }

        for (int i = 0; i < ticketCount; i++){
            tickets.add(ticketQueue.poll());
        }

        notifyAll();
        return tickets;
    }
    public int getRemainingTickets() {
        return ticketQueue.size();
    }
}
