import java.util.List;
import java.util.Random;

public class Customer implements Runnable{
    private int customerID;
    private TicketPool ticketPool;
    private int customerRetrievalRate;
    private int ticketsBought = 0;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int customerID) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerID = customerID;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while(!Thread.interrupted()) {
            try{
                int ticketAmount = rand.nextInt(5) + 1;
                List<Ticket> purchasedTickets = ticketPool.getTicket(ticketAmount);
                if (purchasedTickets.isEmpty()) {
                    System.out.println("Customer " + customerID + " could not buy tickets.");
                }
                else {
                    ticketsBought += purchasedTickets.size();
                    for (Ticket ticket : purchasedTickets) {
                        System.out.println("Customer " + customerID + " bought Ticket " + ticket.getTicketID());
                    }
                }
                    Thread.sleep(customerRetrievalRate);

                } catch (InterruptedException e) {
                System.out.println("Customer ID: " + customerID + " Interrupted");
                break;
            }
        }
    }

    public int getTicketsBought() {
        return ticketsBought;
    }
}
