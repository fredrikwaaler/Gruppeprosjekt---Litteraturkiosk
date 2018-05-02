import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {

    private Register register;

    /**
     * Default constructor for test class RegisterTest
     */
    RegisterTest() {

    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     * Sets up a basic register with some types of prints.
     */
    @BeforeEach
    public void setUp() {

        // First, create the literature intended for the register:
        Book hp1 = new Book("Harry Potter and the philosopher's stone", "Bloomsbury Publishing",
                "J.K Rowling", "1", "26.06.1997");
        Book hp3 = new Book("Harry Potter and the prisoner from Azkaban",
                "Bloomsbury Publishing", "J.K Rowling", "1", "08.07.1999");
        BookSeries harryPotter = new BookSeries("Harry Potter", "Bloomsbury Publishing",
                "J.K Rowling");
        harryPotter.addBook(hp1);
        harryPotter.addBook(hp3);

        Periodical p1 = new Periodical("The Washington Post", "Fred Ryan", "News",
                "Newspaper", "365");
        Periodical p2 = new Periodical("Vogue", "Conde Nast", "Fashion",
                "Magazine", "12");

        // Create the register:
        this.register = new Register();
        this.register.addPrintToRegister(hp1);
        this.register.addPrintToRegister(hp3);
        this.register.addPrintToRegister(harryPotter);
        this.register.addPrintToRegister(p1);
        this.register.addPrintToRegister(p2);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown() {

    }

    int getRegisterSize() {
        int count = 0;
        Iterator<Print> it = this.register.getPrintsInRegister();
        while(it.hasNext()) {
            it.next();
            count += 1;
        }
        return count;
    }

    /**
     * Asserts that it is possible to add a print to the register.
     */
    @Test
    void addPrintToRegister() {
        Book hp2 = new Book("Harry Potter and the Chamber of Secrets", "Bloomsbury Publishing", "J.K Rowling",
                "1", "08.07.1998");
        assertEquals(true, this.register.addPrintToRegister(hp2)); // Should return true if the print is added.
        assertEquals(6, getRegisterSize());
    }

    /**
     * Asserts that it is not possible to add a duplicate.
     */
    @Test
    void negativeAddPrintToRegister() {
        Book hp3 = new Book("Harry Potter and the prisoner from Azkaban",
                "Bloomsbury Publishing", "J.K Rowling", "1", "08.07.1999");
        // Should return false as it isn't possible to add a duplicate.
        assertEquals(false, this.register.addPrintToRegister(hp3));
        assertEquals(5, getRegisterSize()); // Should still be 5 as no books should be added.
    }

    /**
     * Asserts that is it possible to remove a print from the register using the "removePrintFromRegister()" method.
     */
    @Test
    void removePrintFromRegister() {
        assertEquals(true, register.removePrintFromRegister("harry potter and the prisoner from azkaban",
                "bloomsbury publishing")); // Since it exists, it should be removed.
        // We should know only have 4 prints in the register.
        assertEquals(4, getRegisterSize());
    }

    /**
     * Asserts that it will have no effect to try to remove a non-existent print.
     */
    @Test
    void negativeRemovePrintFromRegister() {
        assertEquals(false, register.removePrintFromRegister("Hodejegerne", "Gyldendal"));
        assertEquals(5, getRegisterSize());
    }

    /**
     * Asserts that the search() method of the register works for titles.
     */
    @Test
    void testTitleSearch() {
        List<Print> searchResults = register.search("harry");
        assertEquals(3, searchResults.size()); // Three types of prints contain "harry" in title of publisher.
    }

    /**
     * Asserts that the search() method of the register works for publishers.
     */
    @Test
    void testPublisherSearch() {
        List<Print> searchResults = register.search("BlooMsBury");
        assertEquals(3, searchResults.size()); // Three types of prints contsin "bloomsbury" in publishers name.
    }

    /**
     * Asserts that searching for something that does not exist, returns no result.
     */
    @Test
    void negativeTestSearch() {
        List<Print> searchResults = register.search("Hodejegerne");
        assertEquals(0, searchResults.size());
    }

}
