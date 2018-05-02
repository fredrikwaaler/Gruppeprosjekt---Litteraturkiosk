import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A BookSeries represents a series of books, for instance "Harry Potter".
 * It can hold an infinite number of books associated with the series.
 *
 * @author Fredrik Waaler
 * @author Jørgen Wold
 * @version 04.12.2018
 */

public class BookSeries extends Literature {

    // The books in the series.
    private List<Book> books;

    /**
     * Constructor for objects of type BookSeries.
     * @param title The title of the series.
     * @param publisher The publisher of the series.
     * @param author The author of the series.
     */
    public BookSeries(String title, String publisher, String author) {
        super(title, publisher, author);
        this.books = new ArrayList<>();
    }

    /**
     * Returns an iterator of the books in this series.
     * @return Returns an iterator of the books in this series.
     */
    public Iterator<Book> getBooks() {
        return this.books.iterator();
    }

    /**
     * Checks whether a book with the given title is in this series.
     * @param bookTitle The title of the book to be searched for.
     * @return Returns true if such a book is in the series, else false.
     */
    public boolean checkForBook(String bookTitle) {
        Iterator<Book> it = this.getBooks();
        boolean match = false;
        while (it.hasNext()) {
            Book book = it.next();
            if (book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) {
                match = true;
            }
        }
        return match;
    }

    /**
     * Adds a book to this series list of books.
     * It is not possible to add a book already in the series.
     * @param book The book to add to the series.
     * @return Returns true if the books was added,
     *         else false (In that case, the book already exist in the series)
     */
    public boolean addBook(Book book) {
        boolean added = false;
        boolean alreadyExist = checkForBook(book.getTitle());
        if (!alreadyExist) {
            this.books.add(book);
            added = true;
        }
        return added;
    }

    /**
     * Removes the specified book for the series list of books.
     * The book will, of course, only be removed if it exists in the series.
     * @param titleToRemove The title of the book to remove.
     * @return Returns true if the book was removed, and thus existed in the series.
     *         Else, false will be returned.
     */
    public boolean removeBook(String titleToRemove) {
        boolean removed = false;
        Iterator<Book> it = getBooks();
        while(it.hasNext()) {
            Book book = it.next();
            if (book.getTitle().toLowerCase().equals(titleToRemove.toLowerCase())) {
                it.remove();
                removed = true;
            }
        }
        return removed;
    }

}
