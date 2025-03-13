package com.Ticket2.New.Model;

public class Ticket {
    private int ticketID;
    private static int ticketIDCounter = 1;
    private double price;

    public Ticket() {}

    public Ticket(double price) {
        this.ticketID = ticketIDCounter++;
        this.price = price;
    }

    // Getters and setters
    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID=" + ticketID +
                ", price=" + price +
                '}';
    }
}
