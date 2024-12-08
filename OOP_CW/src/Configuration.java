import java.io.Serializable;

public class Configuration {
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration() {

    }

    public Configuration(int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

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
        this.customerRetrievalRate = customerRetrievalRate * 1000;
    }

    //save conf to JSON File
    //load conf from JSON file

    @Override
    public String toString() {
        return "Configuration{maxTicketCapacity=" + maxTicketCapacity +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", ticketReleaseRate=" + ticketReleaseRate +
                '}';
    }
}
