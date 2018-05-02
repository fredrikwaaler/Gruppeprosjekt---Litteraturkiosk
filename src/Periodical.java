/**
 * The Periodical class represents a periodical print, for example
 * newspaper or any type magazine.
 *
 * @author Fredrik Waaler
 * @author Jørgen Wold
 * @version 04.12.2018
 */

public class Periodical extends Print
{
    //The subject of the periodical print
    private String subject;
    //the type of periodical print
    private String type;
    //The yearly number of yearlyIssues for the print
    private String yearlyIssues;

    /**
     * Default constructor for periodical prints.
     * @param title The title of the periodical print.
     * @param publisher The publisher of the periodical print.
     * @param subject The subject of the periodical print.
     * @param type The type of the periodical print.
     * @param yearlyIssues The nr of yearlyIssues of the periodical print a year.
     */
    public Periodical(String title, String publisher, String subject, String type, String yearlyIssues)
    {
        super(title, publisher);
        this.subject = subject;
        this.type = type;
        this.yearlyIssues = yearlyIssues;
    }

    /**
     *
     * @return subject periodical print
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @return type of periodical print
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return issue number of the periodical print
     */
    public String getYearlyIssues() {
        return this.yearlyIssues;
    }
}
