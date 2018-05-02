import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class BookSeriesTest {

    private BookSeries harryPotter;
    private BookSeries lordOfTheRings;

    /**
     * Default constructor for test class BookTest
     */
    BookSeriesTest() {

    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     * Sets up a couple of series that can be used for testing.
     */
    @BeforeEach
    public void setUp() {
        harryPotter = new BookSeries("Harry Potter", "Bloomsbury Publishing", "J.K Rowling");
        harryPotter.addBook(new Book("Harry Potter and the philosopher's stone", "Bloomsbury Publishing",
                "J.K Rowling", "1", "26.06.1997"));
        harryPotter.addBook(new Book("Harry Potter and the prisoner from Azkaban",
                "Bloomsbury Publishing", "J.K Rowling", "1", "08.07.1999"));

        lordOfTheRings = new BookSeries("The Lord of the Rings", "Allen & Unwin", "J.R.R Tolkien");
        lordOfTheRings.addBook(new Book("The Fellowship of the Ring", "Allen & Unwin",
                "J.R.R Tolkien","1", "29.07.1954"));
        lordOfTheRings.addBook(new Book("The Two Towers", "Allen & Unwin", "J.R.R Tolkien",
                "1", "11.11.1954"));
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown() {

    }

    /**
     * Asserts that the books in the series are actually found using the checkForBook() method.
     */
    @Test
    void checkForBook() {
        assertEquals(true, harryPotter.checkForBook("harry potter and the philosopher's stone"));
        assertEquals(true, lordOfTheRings.checkForBook("ThE TwO ToWeRs"));
    }

    /**
     * Asserts that the checkForBook() method will not return true when used to search for a non-existent book.
     */
    @Test
    void negativeCheckForBook() {
        assertEquals(false, harryPotter.checkForBook("Hodejegerne"));
    }

    /**
     * Asserts that it is possible to add a new book to
     */
    @Test
    void addBook() {
        // The book is not in the HP series from before, and should thus be allowed to add.
        Book newHP = new Book("Harry Potter and the Chamber of Secrets", "Bloomsbury", "J.K Rowling",
                "1", "08.07.1998");
        assertEquals(true, harryPotter.addBook(newHP));

        //It should now exist in the harry potter series:
        assertEquals(true, harryPotter.checkForBook("Harry Potter and the Chamber of Secrets"));
    }

    /**
     * Asserts that it isn't possible to add a book to a series if the series already has such a book.
     */
    @Test
    void negativeAddBook() {
        // The book is in the LOTR series from before, and should thus not be allowed to add.
        Book newLotR = new Book("The Fellowship of the Ring", "Allen & Unwin",
                "J.R.R Tolkien","1", "29.07.1954");
        assertEquals(false, lordOfTheRings.addBook(newLotR));

        int nrOfBooks = 0;
        Iterator<Book> it = lordOfTheRings.getBooks();
        while (it.hasNext()) {
            it.next();
            nrOfBooks += 1;
        }
        assertEquals(2, nrOfBooks); // There should thus still be two books in the LOTR series.
    }

    /**
     * Asserts that it is possible to remove a book from a series using the removeBook method.
     */
    @Test
    void removeBook() {
        // Should return true when it is possible to remove a book.
        assertEquals(true, harryPotter.removeBook("harry potter and the philosopher's stone"));

        // Thus, it should only be one book in the hp series as of now:
        int nrOfBooks = 0;
        Iterator<Book> it = harryPotter.getBooks();
        while (it.hasNext()) {
            it.next();
            nrOfBooks += 1;
        }
        assertEquals(1, nrOfBooks);
    }

    /**
     * Asserts that using the removeBook() method for a non-existent book will have no effect.
     */
    @Test
    void negativeRemoveBook() {
        // Should return true when it is not possible to remove.
        assertEquals(false, lordOfTheRings.removeBook("Ole Brumm på tur"));

        // Thus it should still be two books in the LOTR series:
        int nrOfBooks = 0;
        Iterator<Book> it = lordOfTheRings.getBooks();
        while (it.hasNext()) {
            it.next();
            nrOfBooks += 1;
        }
        assertEquals(2, nrOfBooks);
    }
}