import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConfigurationPrompts {
    private Scanner scanner;

    public ConfigurationPrompts() {
        scanner = new Scanner(System.in);
    }

    public Configuration configure(){
        Configuration conf = new Configuration();

        while(true){
            try{
                System.out.println("Enter Maximum Ticket Capacity(Must be greater than or equal to 5): ");
                int maxTicketCapacity = scanner.nextInt();
                if (maxTicketCapacity >= 5){
                    conf.setMaxTicketCapacity(maxTicketCapacity);
                    break;
                } else{
                    System.out.println("Value must be greater than or equal to 5.");
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid input");
                scanner.next();
            }
        }

        while(true){
            try{
                System.out.println("Enter Customer Retrieval Rate: ");
                int customerRetrievalRate = scanner.nextInt();
                if (customerRetrievalRate > 0){
                    conf.setCustomerRetrievalRate(customerRetrievalRate);
                    break;
                }else{
                    System.out.println("Value must be greater than 0.");
                }

            }catch(InputMismatchException e){
                System.out.println("Invalid input");
                scanner.next();
            }
        }

        while(true){
            try{
                System.out.println("Enter Ticket Release Rate(Tickets per Release). Value must be smaller than or equal to Maximum ticket capacity: ");
                int ticketReleaseRate = scanner.nextInt();
                if (ticketReleaseRate > 0 && ticketReleaseRate <= conf.getMaxTicketCapacity()){
                    conf.setTicketReleaseRate(ticketReleaseRate);
                    break;
                }
                else{
                    System.out.println("Value must be greater than 0.");
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid input");
                scanner.next();
            }
        }
        return conf;
    }

    // Save configuration to file
    public void saveConfigurationToTextFile(Configuration config, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true))) {
            writer.append(config.toString());
            writer.newLine();
            writer.append("Tickets released by Vendor=" + Main.totalTicketsReleased);
            writer.newLine();
            writer.append("Tickets released by Customer=" + Main.totalTicketsBought);
            writer.newLine();
            writer.newLine();
            System.out.println("Configuration saved to text file.");
        } catch (IOException e) {
            System.out.println("Error saving configuration to text file: " + e.getMessage());
        }
    }

    public void saveConfigurationToJson(Configuration config, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Pretty print for readability
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(gson.toJson(config));
            writer.write(System.lineSeparator());
            System.out.println("Configuration saved to JSON file.");
        } catch (IOException e) {
            System.out.println("Error saving configuration to JSON file: " + e.getMessage());
        }
    }

}

