/**
 * The objects of the Event class store and return event details,
 * namely the name of the event and the number of available tickets left for 
 * the event.
 *
 * @author Susanne Christensen
 * @version 28/11/2017
 */
public class Event implements Comparable<Event>
{
    private String name;
    private int tickets;
    /**
     * Create a new Event object.
     * 
     * @param   eventName tickets returned  The name of the event. 
     * @param   numberTickets   The total number of available tickets for the event. 
     */
    public Event(String eventName, int numberTickets)
    {
        name = eventName;
        if (tickets < 0) throw new IllegalArgumentException();
        tickets = numberTickets;
    }

    /**
     * Return the name of the event.
     *
     * @return    The name of the event.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Overrides the toString() method from the object class.
     * 
     * @return  The name of the event and the total number of available tickets.
     */
    @Override
    public String toString()
    {
        return name + ": " + tickets;
    }

    /**
     * Return the total number of available tickets.
     *
     * @return   The total number of available tickets..
     */
    public int checkTickets()
    {
        return tickets;
    }

    /**
     * Decrements the number of available event tickets.
     * 
     * @param   ticketAmount    This amount will be subtracted from total tickets.
     * @return  True if successful, or false if unsuccessful.
     */
    public boolean ticketDecrement(int ticketAmount)
    {
        if(ticketAmount > 0 && ticketAmount <= tickets) {
            tickets = tickets - ticketAmount;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Increments the number of available event tickets.
     * 
     * @param   ticketAmount    This amount will be added to total tickets.
     */
    public boolean ticketIncrement(int ticketAmount)
    {
        if(ticketAmount > 0) {
            tickets = tickets + ticketAmount;
            return true;
        }
        return false;
    }

    /** 
     * Overrides the Comparable interface CompareTo method.
     * 
     * @param    e     The event to compare.
     */
    @Override
    public int compareTo(Event e)
    {
        int nameCmp = name.compareTo(e.name);
        if(nameCmp !=0) return nameCmp;
        else return tickets - e.tickets;

    }
}
