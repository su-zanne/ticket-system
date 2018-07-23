/**
 * This class holds information about the event tickets that a client 
 * may purchase, namely the event and the number of tickets purchased.
 * 
 *
 * @author Susanne Chritstensen
 * @version 10/12/2017
 */
public class Ticket
{
    private Event event;
    private int ticketQuantity;
    /**
     * Create a new Ticket object.
     * 
     * @param event     The event.
     * @param number    The number of tickets the client has purchased for the event.
     */
    public Ticket(Event event, int number)
    {
        this.event = event;
        ticketQuantity = number;
    }

    /**
     * Return the event.
     *
     * @return    The event.
     */
    public Event getEvent()
    {
        return event;
    }

    /**
     * Return the number of tickets the client has purchased for the event.
     * 
     * @return    The ticket quantity.
     */
    public int getQuantity()
    {
        return ticketQuantity;
    }

    /**
     * Decrements the ticket quantity in case of returned tickets.
     * 
     * @param   amount  The number of tickets to return.
     * 
     * @return  True if successful, or false if attempted return was unsuccessful.
     */
    public boolean decrement(int amount)
    {
           if(amount > 0 && amount <= ticketQuantity) {
            ticketQuantity = ticketQuantity - amount;
            System.out.println();
            System.out.println(amount + " ticket(s) returned.");
            return true;
        }
        else {
            System.out.println("Invalid return request. Client order contains " + ticketQuantity + " ticket(s)."); 
            return false;
        }
    }
    
    /**
     * Increments the ticket quantity.
     * 
     * @param   amount  The number of tickets to add.
     * 
     * @return  True if successful, or false if attempted purchase was unsuccessful.
     */
    public boolean increment(int amount)
    {
           if(amount > 0) {
            ticketQuantity = ticketQuantity + amount;
            System.out.println();
            System.out.println(amount + " ticket(s) purchased for: " + event.getName());
            System.out.println();
            return true;
        }
        else {
            System.out.println("Invalid request."); 
            return false;
        }
    }
    
    /**
     * Overrides the toString() method from the object class. 
     * 
     * @return  The name of the event and the number of purchased tickets.
     */
    @Override
    public String toString()
    {
        return event.getName() + " " + ticketQuantity;
    }
}
