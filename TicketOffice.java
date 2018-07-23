import java.io.*;
import java.util.*;
/**
 * This class contains the main method of the programme. It presents the user 
 * with a printed menu from which they can make a selection of different 
 * options: to view details of events and clients, as well as update the
 * information when a client purchases or returns tickets for an event.
 *
 * @author Susanne Christensen
 * @version 06/12/2017
 */
public class TicketOffice
{
    private final static Scanner k = new Scanner(System.in);
    private final static TicketOffice ticketOffice = new TicketOffice();
    private static SortedArrayList<Event> events;
    private static SortedArrayList<Client> clients;
    public static void main(String[] args)
    {     
        try{
            FileReader infile = new FileReader("/users/suzanne/data.txt"); //change to local path
            PrintWriter outfile = new PrintWriter("/users/suzanne/letter.txt"); //change to local path
            readFile(infile);
            printMenu();
            char ch = k.next().charAt(0);
            k.nextLine();
            while (ch != 'f')
            {
                switch(ch)
                {
                    case 'e': listEvents();
                    break;
                    case 'c': listClients();
                    break;
                    case 'b': promptClientName();
                    Client client = searchClient(k.next(), k.next());           
                    if(client != null)
                    {
                        promptEventName();
                        k.nextLine();
                        Event event = searchEvent(k.nextLine());
                        if(event != null) {
                            promptTickets();
                            int tickets = k.nextInt();
                            if(client.prePurchaseCheck(event, tickets, outfile)){
                                client.ticketPurchase(event, tickets);   
                            }
                        }
                    }
                    break;
                    case 'r': promptClientName();
                    Client client2 = searchClient(k.next(), k.next());
                    if(client2 != null) {
                        promptEventName();
                        k.nextLine();
                        Event event2 = searchEvent(k.nextLine());
                        if(event2 != null) {
                            promptReturn();
                            client2.ticketReturn(event2, k.nextInt());   
                        } 
                    }
                    break;
                    default: System.out.println("Invalid entry, try again");
                }
                printMenu();
                ch = k.next().charAt(0);
                k.nextLine();
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found.");  
        }
    }

    /**
     * Create a new TicketOffice object.   
     */
    private TicketOffice()
    {
        events = new SortedArrayList<Event>();   
        clients = new SortedArrayList<Client>();
    }

    /**
     * This method reads from a file. Note: the file format must have 
     * the following format: The first line contains an integer representing the
     * number of available events, and is followed by the information about the 
     * events (two lines for every event: one line containing the name of the 
     * event and the second one containing the number of tickets available for 
     * this event). The nextline contains an integer representing the number of 
     * registered clients, followed by theinformation about the clients 
     * (one line for every client with their first name and surname).
     * 
     * @param    infile     The input file.      
     */
    private static void readFile(FileReader infile)
    {
        Event event;
        Client client;
        Scanner inFile =  new Scanner(infile);
        int numberEvents = inFile.nextInt();
        inFile.nextLine();
        for(int i=0; i<= numberEvents-1; i++){
            event = new Event(inFile.nextLine(),inFile.nextInt());
            events.add(event);
            inFile.nextLine();            
        }
        int numberClients = inFile.nextInt();
        for(int i=0; i<= numberClients-1; i++){
            client = new Client(inFile.next(),inFile.next());
            clients.add(client);
        }
    }

    /**
     * Prints out the names of all stored events.
     */
    public static void listEvents()
    {
        for(Event event : events) {
            System.out.println(event.toString());
        }
    }

    /**
     * Prints out the full name of all clients, followed by information about
     * their purchased events (name of event and number of tickets purchased).
     */
    public static void listClients()
    {
        for(Client client : clients) {
            System.out.println(client.toString());
        }
    }

    /**
     * This method will search stored clients for full name match.
     * 
     * @param   firstName   Search string for client first name.
     * @param   lastName   Search string for client last name.
     * 
     * @return  The client in case of match, or null if no match was found.
     */
    public static Client searchClient(String firstName, String lastName)
    {
        int index = 0;
        boolean searching = true;
        while(searching && index < clients.size()) {
            Client client = clients.get(index);
            String fName = client.getFirstName();
            String lName = client.getLastName();
            if(firstName.equals(fName) && lastName.equals(lName)){
                searching = false;
                return client;
            }
            else {
                index++;   
            }
        }
        System.out.println();
        System.out.println("No clients by that name.");
        System.out.println();
        return null;
    }

    /**
     * This method will search stored events for name match.
     * 
     * @param   eventName   Search string for event name.
     * 
     * @return  The event in case of match, or null if no match was found.
     */
    public static Event searchEvent(String eventName)
    {
        int index = 0;
        boolean searching = true;
        while(searching && index < events.size()) {
            Event event = events.get(index);
            String eName = event.getName();
            if(eventName.equals(eName)){
                searching = false;
                return event;
            }
            else {
                index++;   
            }
        }
        System.out.println();
        System.out.println("No events by that name.");
        System.out.println();
        return null;
    }

    /**
     * Prints the main menu.
     */
    private static void printMenu()
    {
        System.out.println("------------------------------");
        System.out.println("MENU");
        System.out.println("f - finish");
        System.out.println("e - list all events");
        System.out.println("c - list all clients");
        System.out.println("b - update data after ticket purchase");
        System.out.println("r - update data after ticket cancellation");
        System.out.println("------------------------------");
        System.out.println("Type a letter and press Enter");
    }

    /**
     * Prints the client name prompt.
     */
    private static void promptClientName()
    {
        System.out.println();
        System.out.print("Type first name and surname of client: ");
        System.out.println();
    }

    /**
     * Prints the event name prompt.
     */
    private static void promptEventName()
    {
        System.out.println();
        System.out.print("Type the name of the event: ");
        System.out.println();
    }

    /**
     * Prints the number of tickets to purchase prompt.
     */
    private static void promptTickets()
    {
        System.out.println();
        System.out.print("Enter number of tickets to purchase: ");
        System.out.println();
    }

    /**
     * Prints the number of tickets to return prompt.
     */ 
    private static void promptReturn()
    {
        System.out.println();
        System.out.print("Enter number of tickets to return: ");
        System.out.println();
    }
}