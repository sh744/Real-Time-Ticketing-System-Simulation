import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TicketPool {
    private ConcurrentLinkedQueue<Ticket> ticketQueue;
    private int maxTicketCapacity; //Max amount of tickets that can be held.

    public TicketPool(int maxTicketCapacity) {
        ticketQueue = new ConcurrentLinkedQueue<>();
        this.maxTicketCapacity = maxTicketCapacity; //if not for this, will throw null pointer exception
    }

    public synchronized void addTickets(Ticket ticket){
        while (ticketQueue.size() >= maxTicketCapacity) {
            try {
                System.out.println("Ticket buffer is full!");
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
                System.out.println("Waiting for the ticket to be available.");
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
}
