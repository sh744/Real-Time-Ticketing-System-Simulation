import java.util.Random;

public class Vendor implements Runnable{
    private TicketPool ticketPool;
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;
    private int ticketsReleased = 0;

    public Vendor(TicketPool ticketPool, int vendorId, int ticketsPerRelease, int releaseInterval) {
        this.ticketPool = ticketPool;
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(releaseInterval);

                for (int i = 0; i < ticketsPerRelease; i++) {
                    Ticket ticket = new Ticket(50.00);

                    ticketPool.addTickets(ticket);
                    ticketsReleased++; //
                }


                System.out.println("Vendor " + vendorId + " released " + ticketsPerRelease + " tickets.");

            } catch (InterruptedException e) {
                System.out.println("Vendor " + vendorId + " was interrupted.");
                break;
            }
        }
    }


    public int getTicketsReleased(){
        return ticketsReleased;
    }
}
