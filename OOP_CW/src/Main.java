import java.util.Scanner;

public class Main {
    public static int totalTicketsBought = 0;
    public static int totalTicketsReleased = 0;
    public static void main(String[] args) {
        ConfigurationPrompts configPrompts = new ConfigurationPrompts();
        Configuration config = configPrompts.configure();
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        Thread[] vendorThreads = new Thread[2];
        Vendor[] vendors = new Vendor[2];

        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new Vendor(ticketPool,i+1, config.getTicketReleaseRate(), 1000);
            vendorThreads[i] = new Thread(vendors[i]);
            vendorThreads[i].start();
        }

        Thread[] customerThreads = new Thread[5];
        Customer[] customers = new Customer[5];

        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(ticketPool, config.getCustomerRetrievalRate(), i+1);
            customerThreads[i] = new Thread(customers[i]);
            customerThreads[i].start();
        }

        System.out.println("Simulation running. Press Enter to exit.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

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
            System.out.println("Error stopping threads.");
        }

        System.out.println("\nSimulation stopped.");
        System.out.println(config);

        for (Vendor vendor : vendors) {
            totalTicketsReleased += vendor.getTicketsReleased();
        }
        System.out.println("Total Tickets Released by Vendors: " + totalTicketsReleased);


        for (Customer customer : customers) {
            totalTicketsBought += customer.getTicketsBought();
        }
        System.out.println("Total Tickets Purchased by Customers: " + totalTicketsBought);

        configPrompts.saveConfigurationToTextFile(config,"file.txt");
        configPrompts.saveConfigurationToJson(config,"file.json");

    }
}