import java.sql.SQLOutput;
import java.util.*;

/**
 * Makes up the user interface (text based) of the application.
 * Responsible for all user interaction, like displaying the menu
 * and receiving input from the user.
 *
 * @author Fredrik Waaler
 * @author Jørgen Wold
 * @version 04.12.2018
 */

public class ApplicationUI
{

    private Register printRegister;

    // The main menu that will be displayed.
    private String[] menuItems =
            {
                    "1. List all prints",
                    "2. Add new print",
                    "3. Add book to book series",
                    "4. Remove print",
                    "5. Search for print",
            };

    /**
     * Creates an instance of the ApplicationUI User interface.
     */
    public ApplicationUI()
    {
        this.printRegister = new Register();
    }

    public static void main(String[] args) {
        ApplicationUI ui = new ApplicationUI();
        ui.start();
    }

    /**
     * Starts the application by showing the menu and retrieving input from the
     * user.
     */
    public void start()
    {
        boolean quit = false;

        while (!quit)
        {
            try
            {
                System.out.println("\n**** Application v3.0 ****\n");
                int menuSelection = this.showMenu(menuItems, "Exit");
                switch (menuSelection)
                {
                    case 1:
                        this.listAllPrints();
                        break;

                    case 2:
                        this.addPrint();
                        break;

                    case 3:
                        this.addBookToSeries();
                        break;

                    case 4:
                        this.removePrint();
                        break;

                    case 5:
                        this.searchForPrint();
                        break;

                    default:
                        System.out.println("\nThank you for using the application. Bye!\n");
                        quit = true;
                        break;
                }
            }
            catch (InputMismatchException ime)
            {
                System.out.println("\nERROR: Please provide a number between 1 and "
                        + (this.menuItems.length + 1) + "..\n");
            }
        }
    }

    /**
     * Displays the menu to the user, and waits for the users input. The user is
     * expected to input an integer between 1 and the max number of menu items.
     * If the user inputs anything else, an InputMismatchException is thrown.
     * The method returns the valid input from the user.
     *
     * @param menu An array of th menu items to display. (For instance: "1. add", "2. remove")...
     * @param exitChoice Is added to the bottom of the menu for whatever exit choice is reasonable.
     *                   I.e. "exit", "cancel", "back", etc.
     * @return the menu number (between 1 and max menu item number) provided by the user.
     * @throws InputMismatchException if user enters an invalid number/menu choice
     */
    private int showMenu(String[] menu, String exitChoice) throws InputMismatchException
    {
        // Display the menu
        for (String menuItem : menu)
        {
            System.out.println(menuItem);
        }
        int maxMenuItemNumber = menu.length + 1;

        // Add the "Exit"-choice to the menu
        System.out.println(maxMenuItemNumber + ". " + exitChoice + "\n");
        System.out.print("Please choose menu item (1-" + maxMenuItemNumber + "): ");

        // Read input from user
        Scanner reader = new Scanner(System.in);
        int menuSelection = reader.nextInt();

        if ((menuSelection < 1) || (menuSelection > maxMenuItemNumber))
        {
            throw new InputMismatchException();
        }
        return menuSelection;
    }

    // ------ The methods below this line are "helper"-methods, used from the menu ----
    // ------ All these methods are made private, since they are only used by the menu ---

    /**
     * Lists all the prints in the register.
     * If there are no prints, prompt a info message to declare that.
     */
    private void listAllPrints()
    {
        Iterator<Print> it = this.printRegister.getPrintsInRegister();
        if (!it.hasNext()) {
            System.out.println("The register is empty.\n" +
                    "Add prints to the register by selecting \"Add new print\" in the main menu");
        }
        else {
            while (it.hasNext()) {
                Print p = it.next();
                String representationOfP = p.getTitle() + " (" + p.getPublisher() + ")";
                System.out.println(representationOfP);
            }
        }
    }

    /**
     * Takes user input to create a print.
     * The type of the print is determined by the input printNumber.
     * 1 --> Periodical
     * 2 --> Book
     * 3 --> BookSeries
     * @param printNumber Determines the type of print to create.
     * @return Returns the print created based on the user input.
     */
    private Print createPrint(int printNumber) {

        Print returnPrint = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Publisher: ");
        String publisher = sc.nextLine();

        switch (printNumber) {
            case 1:
                System.out.print("Subject: ");
                String subject = sc.nextLine();
                System.out.print("Type: ");
                String type = sc.nextLine();
                System.out.print("Yearly Issues: ");
                String yearlyIssues = sc.nextLine();

                returnPrint = new Periodical(title, publisher, subject, type, yearlyIssues);
                break;

            case 2:
            case 3:
                System.out.print("Author: ");
                String author = sc.nextLine();

                switch(printNumber) {
                    case 2:
                        System.out.print("Edition: ");
                        String edition = sc.nextLine();
                        System.out.print("Publishing Date: ");
                        String publishingDate = sc.nextLine();

                        returnPrint = new Book(title, publisher, author, edition, publishingDate);
                        break;

                    case 3:
                        returnPrint = new BookSeries(title, publisher, author);
                        break;
                }
                break;
        }
        return returnPrint;
    }

    /**
     * Takes user input from the user to add a print to the register.
     */
    private void addPrint() {
        String[] menu = {
                "1. Periodical",
                "2. Book",
                "3. BookSeries"
        };

        boolean quit = false;
        int choice = 0;
        while (!quit) {
            try {
                choice = showMenu(menu, "Back");
                if (choice == (menu.length + 1)) { quit = true; }
                else {
                    Print printToAdd = createPrint(choice);
                    if (!printRegister.addPrintToRegister(printToAdd)) {
                        System.out.println("Oops! Print not added as it already exists in the register.");
                    }
                    else {
                        System.out.println(printToAdd.getTitle() + " succesfully added to register.");
                    }
                    quit = true;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nERROR: Please provide a number between 1 and "
                        + (menu.length + 1) + "..\n");
            }
        }
    }

    /**
     * Takes input from the user to perform a search in the
     * register by publishers name, og prints title.
     */
    private void searchForPrint() {
        System.out.print("Title of publisher to search by: ");
        Scanner sc = new Scanner(System.in);
        String searchKey = sc.nextLine();

        List<Print> prints = printRegister.search(searchKey);
        if(prints.isEmpty()) {
            System.out.println("No results found.");
        }
        else {
            System.out.println("Found the following result(s): ");
            for (Print p : prints) {
                System.out.println("\t" + p.getTitle() + " (" + p.getPublisher() + ")");
            }
        }
    }

    /**
     * Removes a print from the register based on user input.
     */
    private void removePrint() {
        System.out.println("Please provide title and publisher for the print you wish to remove: ");
        Scanner sc = new Scanner(System.in);
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Publisher: ");
        String publisher = sc.nextLine();

        boolean removed = this.printRegister.removePrintFromRegister(title, publisher);
        if (removed) {
            System.out.println("\"" + title + "\"" + " was removed from register.");
        }
        else {
            System.out.println("\"" + title + "\"" + " was not found in register, and thus not removed.");
        }
    }

    /**
     * Allows the user to add a book in the register, to a book series in the register.
     */
    private void addBookToSeries() {
        System.out.print("Add book to the following series: ");
        Scanner sc = new Scanner(System.in);
        String seriesTitle = sc.nextLine();

        boolean quit = false;
        while (!quit) {
            boolean match = false;
            BookSeries bookSeries = null;
            Iterator<Print> it = this.printRegister.getPrintsInRegister();
            while (it.hasNext() && !match) {
                Print p = it.next();
                if (p.getTitle().toLowerCase().equals(seriesTitle.toLowerCase())) {
                    bookSeries = (BookSeries) p;
                    match = true;
                }
            }

            if (bookSeries == null) {
                System.out.println("No such series in the register. Try again.");
                quit = true;
            } else {
                System.out.println("Ready to add book to the series: " + bookSeries.getTitle());
            }

            if (!quit) {
                System.out.print("Name of book to add: ");
                String bookTitle = sc.nextLine();

                match = false;
                Book resultBook = null;
                Iterator<Print> iter = this.printRegister.getPrintsInRegister();
                while (iter.hasNext() && !match) {
                    Print p = iter.next();
                    if (p instanceof Book) {
                        if (p.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) {
                            resultBook = (Book) p;
                            match = true;
                        }
                    }
                }
                if (resultBook == null) {
                    System.out.println("No such book found. Try again.");
                } else {
                    if (bookSeries.addBook(resultBook)) {
                        System.out.println("\"" + resultBook.getTitle() + "\"" + " successfully added to "
                                + "\"" + bookSeries.getTitle() + "\"");
                    }
                    else {
                        System.out.println("Book already exist in series and was thus not added.");
                    }
                    quit = true; // Quit anyway.
                }
            }
        }


    }
}