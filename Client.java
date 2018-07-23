import java.io.*;
import java.util.*;
/**
 * The objects of the Client class store and return client details:
 * the first and last name of the client; the purchased event(s); and
 * the number of tickets bought for the event(s).
 *
 * @author Susanne Christensen
 * @version 28/11/2017
 */
public class Client implements Comparable<Client>
{
    private static final int MAX_EVENTS = 3;
    private String firstName;
    private String lastName;
    private Ticket[] events;
    /**
     * Create a new client object.
     * 
     * @param   firstName    The first name of the client.
     * @param   lastName     The last name of the client.    
     */
    public Client(String fName, String lName)
    {
        firstName = fName;
        lastName = lName;
        events = new Ticket[MAX_EVENTS];
    }

    /**
     * Return the first name of the client.
     *
     * @return    The full name of the client.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Return the last name of the client.
     *
     * @return    The last name of the client.
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Overrides the toString() method from the object class.
     * 
     * @return  The full name of client and their purchased events.
     */
    @Override
    public String toString()
    {
        return firstName + " " + lastName + ": " + Arrays.toString(events);
    }

    /**
     * Overrides the Comparable interface CompareTo method.
     * 
     * @param    c      Client object to compare.
     */
    @Override
    public int compareTo(Client c)
    {
        int lnCmp = lastName.compareTo(c.lastName);
        if(lnCmp !=0) return lnCmp;
        int fnCmp = firstName.compareTo(c.firstName);
        if(fnCmp !=0) return fnCmp;
        else return 0;
    }

    /**
     * Runs a check to see if there are enough tickets left for the event, and
     * then whether the client has already purchased tickets for the event on
     * an earlier occassion.
     * 
     * @param   event   The event to purchase tickets for. 
     * @param   tickets The number of tickets to purchase.
     * @param   outfile The file to which the letter will be printed in case of too few tickets left.
     * @return  True if check was successful, or false if unsuccessful.
     */
    public boolean prePurchaseCheck(Event event, int tickets, PrintWriter outfile)
    {
        if(tickets <= 0) {
            System.out.println(); 
            System.out.println("Not valid input");
            return false; 
        }
        if(tickets > 0 && tickets > event.checkTickets()){
            printLetter(event, tickets, outfile);
            System.out.println();
            System.out.println("Not enough tickets left. A note in the form of a letter");
            System.out.println("printed to file."); 
            return false;
        }
        for(int i = 0; i<events.length; i++) {
            if(events[i] != null && events[i].getEvent() == event){
                events[i].increment(tickets);
                event.ticketDecrement(tickets);
                return false;
            }
        }
        return true;
    }

    /**
     * Purchase tickets for an event.
     * 
     * @param   event   The event to purchase tickets for. 
     * @param   tickets The number of tickets to purchase.
     * 
     * @return  True if tickets are purchased successfully, or false if unsuccessful.
     */
    public boolean ticketPurchase(Event event, int tickets) 
    {
        for(int i = 0; i<events.length; i++) {
            if (events[i] == null) {
                events[i] = new Ticket(event,(tickets));
                System.out.println();
                System.out.println(tickets + " ticket(s) purchased for: " + event.getName());
                System.out.println();
                event.ticketDecrement(tickets);
                return true;   
            }
            else if(i == events.length-1) {
                System.out.println();
                System.out.println("No tickets purchased. Client already has tickets for maximum of " + MAX_EVENTS + " events.");
                return false;
            }
        }
        return false;
    }

    /**
     * Return tickets for an event.
     * 
     * @param   event   The event to return tickets for. 
     * @param   tickets The number of tickets to return. 
     * 
     * @return  True if tickets are returned successfully, or false if unsuccessful.
     */
    public boolean ticketReturn(Event event, int tickets)
    {
        for(int i = 0; i<events.length; i++) {
            if(events[i] != null && events[i].getEvent() == event){
                if(!events[i].decrement(tickets)) {
                    return false;
                }
                else {
                    event.ticketIncrement(tickets);
                }
                if(events[i].getQuantity() == 0)
                {
                    events[i] = null; // clears the Array box if all tickets are returned.
                }
                return true;   
            }
        }
        System.out.println();
        System.out.println("Client does not have any tickets for this event.");
        return false;
    }  

    /**
     * Prints a letter to a file informing client that there are not enough
     * tickets available.
     * 
     * @param   event   The event the client attempted to buy tickets for. 
     * @param   tickets The number of tickets the client attempted to buy.   
     * @param   outfile The file to which the letter will be printed.
     */
    public void printLetter(Event event, int tickets, PrintWriter outfile)
    {
        outfile.println("Dear " + getFirstName() + " " + getLastName() + ",");
        outfile.println();
        outfile.println("Unfortunately, your order of " + tickets + " ticket(s) for the"); 
        outfile.println(event.getName() + " event has not been completed.");
        outfile.println();
        outfile.println("There are " + event.checkTickets() + " ticket(s) left for this event.");
        outfile.println();
        outfile.println("Kind regards,");
        outfile.println();
        outfile.println("The Ticket Office");
        outfile.close();
    }  
}