/**
 * The literature represents a type of print. However, it is somehow more formal.
 * Thus it has, for instance, a specified author.
 *
 * @author Fredrik Waaler
 * @author Jørgen Wold
 *
 * @version 04.12.2018
 */

public abstract class Literature extends Print {

    // The author of the literature
    private String author;

    /**
     * Constructor for objects of type literature.
     * @param title The title of the literature.
     * @param publisher The publisher of the literature.
     * @param author The author of the literature.
     *
     */
    public Literature(String title, String publisher, String author) {
        super(title, publisher);
        this.author = author;
    }

    /**
     * Returns the author of the literature.
     * @return Returns the author of the literature.
     */
    public String getAuthor() {
        return this.author;
    }

}
