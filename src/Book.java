/**
 * The book class represents a single book.
 * A book is a type of literature.
 *
 * @author Fredrik Waaler
 * @author Jørgen Wold
 * @version 04.12.2018
 */

public class Book extends Literature {

    // The edition of the book
    private String edition;
    // The publishing date of the book
    private String publishingDate;

    /**
     * Constructor for objects of type Book.
     * @param title The title of the book.
     * @param publisher The publisher of the book.
     * @param author The author of the book.
     * @param edition The edition of the book (I.e 1 (first), 2 (second)...)
     * @param publishingDate The publishing date of the book. (Type 27.04.1997)
     */
    public Book(String title, String publisher, String author, String edition, String publishingDate) {
        super(title, publisher, author);
        this.edition = edition;
        this.publishingDate = publishingDate;
    }

    /**
     * Checks whether another object is equal to this book.
     * Two object must, first of all, be a book to match this book.
     * They are equal if the occupate the same address in memory,
     * or if their author and title equals.
     * @param object The object we wish to compare to.
     * @return Returns true if the books equals, else false.
     */
    public boolean equals(Object object) {
        boolean match = false;
        if (object instanceof Book) {
            Book book = (Book) object;
            if ((book.getTitle().equals(this.getTitle())) && (book.getAuthor().equals(this.getAuthor()))) {
                match = true;
            }
        }
        return match;
    }

    /**
     * Returns the edition of the book.
     * @return Returns the edition of the book.
     */
    public String getEdition() {
        return this.edition;
    }

    /**
     * Returns the publishing date of the book.
     * @return Returns the publishing date of the book.
     */
    public String getPublishingDate() {
        return this.publishingDate;
    }
}
