import java.util.Scanner;

public class MyMidtermLabExam {
    static String[] issueDescription = new String[5];
    static String[] urgencyLevel = new String[5];
    static String[] status = new String[5];
    static int total = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            mainMenu();
        }
    }

    public static void mainMenu() {
        System.out.println();
        System.out.println("=== IT Ticket System ===");
        System.out.println("1. Add Ticket");
        System.out.println("2. Update Ticket Status");
        System.out.println("3. Show All Tickets");
        System.out.println("4. Generate Report");
        System.out.println("5. Exit");
        System.out.print("Enter Choice: ");

        int enterChoice = scanner.nextInt();
        scanner.nextLine();
        int choice = 0;

        if (enterChoice == 1 || enterChoice == 2 || enterChoice == 3 || enterChoice == 4 || enterChoice == 5){
            if (enterChoice == 1) choice = 1;
            if (enterChoice == 2) choice = 2;
            if (enterChoice == 3) choice = 3;
            if (enterChoice == 4) choice = 4;
            if (enterChoice == 5) choice = 5;
        } else {
            System.out.println("Invalid input. Please enter a number 1-5.");
            return;
        }

        switch (choice) {
            case 1:
                addTicket();
                break;
            case 2:
                updateTicketStatus();
                break;
            case 3:
                showTickets();
                break;
            case 4:
                generateReport();
                break;
            case 5:
                System.out.println("Exiting... Thank you!");
                System.exit(0);
        }
    }

    public static void addTicket(){
        if (total < 5) {
            System.out.print("Enter issue description: ");
            String issue = scanner.nextLine();

            System.out.print("Enter urgency (Low / Medium / High): ");
            String urgency = scanner.nextLine();

            issueDescription[total] = issue;
            urgencyLevel[total] = urgency;
            status[total] = "Pending";
            total = total + 1;

            System.out.println("Ticket successfully added!");
        } else {
            System.out.println("Maximum ticket limit reached.");
        }
    }

    public static void updateTicketStatus() {
        System.out.print("Enter ticket number to update: ");
        int ticketNum = scanner.nextInt();
        scanner.nextLine();
        int num = 0;
        if (ticketNum == 1) num = 1;
        else if (ticketNum == 2) num = 2;
        else if (ticketNum == 3) num = 3;
        else if (ticketNum == 4) num = 4;
        else if (ticketNum == 5) num = 5;
        else {
            System.out.println("Invalid ticket number.");
            return;
        }

        if (num <= total) {
            if (status[num - 1].equals("Resolved")) {
                System.out.println("This ticket is already resolved.");
            } else {
                System.out.print("Enter new status (In Progress / Resolved): ");
                String newStatus = scanner.nextLine();
                if (newStatus.equals("In Progress") || newStatus.equals("Resolved")) {
                    status[num - 1] = newStatus;
                    System.out.println("Ticket status updated.");
                } else {
                    System.out.println("Invalid status.");
                }
            }
        } else {
            System.out.println("Ticket does not exist.");
        }
    }

    public static void showTickets() {
        System.out.println("--- All Tickets ---");
        for (int i = 0; i < total; i++) {
            System.out.println((i + 1) + ". [" + urgencyLevel[i] + "] "+ issueDescription[i] + " - Status: "  + status[i]);
        }
    }

    public static void generateReport() {
        int pending = 0;
        int resolved = 0;

        for (int i = 0; i < total; i++) {
            if (status[i].equals("Resolved")) {
                resolved = resolved + 1;
            } else {
                pending = pending + 1;
            }
        }
        
        System.out.println();
        System.out.println("--- Ticket Report ---");
        System.out.println("Total Tickets: " + total);
        System.out.println("Pending Tickets: " + pending);
        System.out.println("Resolved Tickets: " + resolved);
    }
}