// advance problems 1, 2, 3, 4, 5, 6, 7, 8
// I get help from learning center and classmate Jessy

package com.marian;




import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

public class Main {
    //public static String resolvedTicket;
    Scanner scan = new Scanner(System.in);
     static LinkedList<Ticket> resolvedTickets = new LinkedList<>();

    public static void main(String[] args) throws IOException {

        LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();


        Scanner scan = new Scanner(System.in);

// create variable on open ticket
        String openfile = "open_tickets.txt";
        //to create bufferedreader to read the file on open_ticket
        try (BufferedReader reader = new BufferedReader(new FileReader(openfile))){
           // reading line from the file
            String line = reader.readLine();
            //to split the line on space between the file
            while (line !=null){
                String[] lineDate = line.split(" ");
//to store data position on the array id, pritority, description, report on the open_ticket
                int id = Integer.parseInt(lineDate[1]);
                String description = lineDate[3];
                int pritority = Integer.parseInt( lineDate[5]);
                String report = lineDate[8];
                //String dateString =
                // create simpleDataform to create date
                SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                //creating variable data on linedate
                String dateInString = lineDate[11] + " " + lineDate[12] + " " + lineDate[13] + " " + lineDate[14]
                        + " " + lineDate[15] + " " + lineDate[16];

                try {
//to create date information to use formatter
                    Date date = formatter.parse(dateInString);
                    //to create open_ticket instance in ticket class and add data licketedList
                    Ticket openTicket = new Ticket(description, pritority, report, date, id);
                    ticketQueue.add(openTicket);

//catching file relate exception
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                line = reader.readLine();









            }
            reader.close();


        }catch (IOException se){
            System.out.println("error creating file " + se);
        }



        while (true) {
// display user choice if they want to add ticket, delete ticket, display all ticket, detelet ticket by issue, search by name  or quit the program
            System.out.println("1. Enter Ticket\n2. Delete Ticket\n3. Display All Tickets\n4. delete by issue\n5. " +
                    "search by name\n6. Quit");
            int task = Integer.parseInt(scan.nextLine() );
            //give user to choice to choose between 1 to 6
            if (task <= 0 || task > 6) {
                System.out.println("pick number between 1 to 6 \n");
                continue;
            }

            if (task == 1) {
                //Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);

            } else if (task == 2) {
                //delete a ticket
                deleteTicket(ticketQueue);
            } else if (task == 6) {
                // to quit the program
                System.out.println("quit");
                break;

            } else if (task == 4) {
                //delete ticket by issue
                deletebyissue(ticketQueue);

            } else if (task == 5) {
                //to search ticket by name
                searchbyname(searchbyname(ticketQueue));
            } else {
                //this will happen for 3 or any other selection that is a valid int
                //TODO Program crashes if you enter anything else - please fix
                //Default will be print all tickets
                printAllTickets(ticketQueue);
            }


        }


        scan.close();
        Date date = new Date();
        // to store date to print resolved date
        SimpleDateFormat sdf = new SimpleDateFormat("MMM_dd_yyyy");

        String resolve = sdf.format(date);
        String file = "resolved date " + resolve + "txt";
        String open = "open_tickets.txt";
        // to create bufferewriter to write file  on the system
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        ) {
            BufferedWriter bwrite = new BufferedWriter(new FileWriter(open));

            for (Ticket t : resolvedTickets) {

                writer.write(t.toString() + "\n");
            }
            for (Ticket B : ticketQueue) {
                bwrite.write(B.toString() + "\n");
            }
            writer.close();
            bwrite.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void   deletebyissue(LinkedList<Ticket> ticketQueue) {
        Scanner scan = new Scanner(System.in);
        boolean found = false;
        System.out.println("what is issue of the ticket");
        String deletebyissue = scan.nextLine();
        for (Ticket ticket : ticketQueue) {
            if (ticket.getDescription().contains(deletebyissue)) {
                System.out.println("how you solve");
                String solve = scan.next();
                ticket.setResolution(solve);
                ticket.setResolutionDate(new Date());
                ticketQueue.remove(ticket);
                resolvedTickets.add(ticket);
                found = true;

            }


        }

    }

    public static LinkedList<Ticket> searchbyname(LinkedList<Ticket> ticketQueue) {
        Scanner s = new Scanner(System.in);
        boolean found = false;
        LinkedList<Ticket> tick = new LinkedList<>();
        System.out.println("search ticket by name");
        String searchbyname = s.nextLine();
        for (Ticket ti : ticketQueue) {
            if (ti.getDescription().contains(searchbyname)) {


                tick.add(ti);
                found = true;

            }

        }
        return tick;
    }


            protected static void deleteTicket(LinkedList<Ticket> ticketQueue) {

                printAllTickets(ticketQueue);
                while (true) {


                    Scanner deleteScanner = new Scanner(System.in);
                    System.out.println("Enter ID of ticket to delete");
                    int deleteID = deleteScanner.nextInt();
                    //Loop over all tickets. Delete the one with this ticket ID
                    boolean found = false;
                    for (Ticket ticket : ticketQueue) {
                        if (ticket.getTicketID() == deleteID) {
                            found = true;
                            System.out.println("How did you resolve the issue? ");
                            ticket.setResolution(deleteScanner.next());
                            ticket.setResolutionDate(new Date());
                            ticketQueue.remove(ticket);
                            System.out.println(String.format("Ticket %d deleted", deleteID));
                            resolvedTickets.add(ticket);
                            //modify
                            break; //don't need loop any more.
                        }
                    }


                    if (found == false) {
                        System.out.println("Ticket ID not found, enter ticketID again\n");
                        //TODO � re-write this method to ask for ID again if not found

                        continue;



                    }
                    printAllTickets(ticketQueue);  //print updated list
                    break;


                }


            }




            //Move the adding ticket code to a method
            protected static void addTickets(LinkedList<Ticket> ticketQueue) {
                Scanner sc = new Scanner(System.in);

                boolean moreProblems = true;
                String description;
                String reporter;
                //let's assume all tickets are created today, for testing. We can change this later if needed
                Date dateReported = new Date(); //Default constructor creates date with current date/time
                int priority;

                while (moreProblems) {
                    System.out.println("Enter problem");
                    description = sc.nextLine();
                    System.out.println("Who reported this issue?");
                    reporter = sc.nextLine();
                    System.out.println("Enter priority of " + description);
                    priority = Integer.parseInt(sc.nextLine());

                    Ticket t = new Ticket(description, priority, reporter, dateReported);
                    ticketQueue.add(t);

                    //To test, let's print out all of the currently stored tickets
                    printAllTickets(ticketQueue);

                    System.out.println("More tickets to add?");
                    String more = sc.nextLine();
                    if (more.equalsIgnoreCase("N")) {
                        moreProblems = false;
                    }
                }
            }
    protected static void addTicketInPriorityOrder(LinkedList<Ticket> tickets, Ticket newTicket) {

        //Logic: assume the list is either empty or sorted

        if (tickets.size() == 0) {//Special case - if list is empty, add ticket and return
            tickets.add(newTicket);
            return;
        }

        //Tickets with the HIGHEST priority number go at the front of the list. (e.g. 5=server on fire)
        //Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end

        int newTicketPriority = newTicket.getPriority();

        for (int x = 0; x < tickets.size(); x++) {    //use a regular for loop so we know which element we are looking at

            //if newTicket is higher or equal priority than the this element, add it in front of this one, and return
            if (newTicketPriority >= tickets.get(x).getPriority()) {
                tickets.add(x, newTicket);
                return;


            }
        }
    }
            protected static void printAllTickets(LinkedList<Ticket> tickets) {
                System.out.println(" ------- All open tickets ----------");

                for (Ticket t : tickets ) {
                    System.out.println(t); //Write a toString method in com.marian.Ticket class
                    //println will try to call toString on its argument
                }
                System.out.println(" ------- End of ticket list ----------");

            }



}





