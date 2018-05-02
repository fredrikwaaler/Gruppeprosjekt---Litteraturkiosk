/**
 * The print class represents any type of print.
 * It could be a whole series of books, a single book, or simply a magazine.
 *
 * @author Fredrik Waaler
 * @author Jørgen Wold
 *
 * @version 04.12.2018
 */

public abstract class Print {

    // The title of the print
    private String title;
    // The publisher of the print.
    private String publisher;

    /**
     * Constructor for single print.
     * @param title The title of the print.
     * @param publisher The publisher of the print.
     */
    public Print(String title, String publisher) {
        this.title = title;
        this.publisher = publisher;
    }

    /**
     * Returns the title of the print.
     * @return Returns the title of the print.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the publisher of the print.
     * @return Returns the publisher of the print.
     */
    public String getPublisher() {
        return this.publisher;
    }

    /**
     * Checks whether or not the specified object is equal to the print itself.
     * (I.e. it is a instance of print, and its publisher and title equals)
     * @param object The object to check if equals.
     * @return Returns true if they are equal, else false.
     */
    public boolean equals(Object object) {
        boolean match = false;
        if (object instanceof Print) {
            Print print = (Print) object;
            if ((print.getTitle().equals(this.getTitle())) && (print.getPublisher().equals(this.getPublisher()))) {
                match = true;
            }
        }
        return match;
    }

    /**
     * Returns a string representation of the print.
     * Note: Only for debugging purposes!
     * @return Returns a string for representation of the print.
     */
    public String toString() {
        return "<" + this.getTitle() + ">";
    }
}








































