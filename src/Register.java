import java.util.*;

/**
 * The register class is meant to hold journals of different types (I.e. for libraries, kiosks, etc.).
 * It will contain an internal register of prints on which you can call methods for different actions.
 *
 * @author Fredrik Waaler
 * @author Jørgen Wold
 * @version 04.12.2018
 *
 */
public class Register implements Iterable<Print> {

    private List<Print> prints;  // A register for the books.

    /**
     * Constructor for the Register class.
     */
    public Register() {
        prints = new ArrayList<>();
    }

    /**
     * Adds a print to the register of prints.
     * Will not allow for adding of duplicates.
     *
     * @param print The specified print
     * @return Returns true if the print was added,
     * thus no duplicate already existed in the register.
     * Else, false is returned.
     */
    public boolean addPrintToRegister(Print print) {
        boolean added = false;
        if (!existsInRegister(print.getTitle(), print.getPublisher())) {
            this.prints.add(print);
            added = true;
        }
        return added;
    }

    /**
     * Returns an iterator of the prints in the register.
     * @return Returns an iterator of the prints in the register.
     */
    public Iterator<Print> iterator() {
        return this.prints.iterator();
    }

    /**
     * Removes a print from the register of prints
     *
     * @param title     The title of the print to remove.
     * @param publisher The publisher of the print to remove.
     * @return Returns true if the object is removed, and thus it existed in the register.
     * Else, false.
     */
    public boolean removePrintFromRegister(String title, String publisher) {
        boolean removed = false;
        Iterator<Print> it = this.getPrintsInRegister();
        while (it.hasNext() && !removed) {
            Print p = it.next();
            if ((p.getTitle().toLowerCase().equals(title.toLowerCase()))
                    && (p.getPublisher().toLowerCase().equals(publisher.toLowerCase()))) {
                it.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Returns a string of all the books in the register
     *
     * @return Returns all the books in the register, if there are none, return null.
     */
    public Iterator<Print> getPrintsInRegister() {
        return this.prints.iterator();
    }

    /**
     * Search for a print by the specified prefix.
     *
     * @param keyPrefix The publisher to search by.
     * @return Returns a list of the prints found.
     */
    public List<Print> search(String keyPrefix) {
        SortedMap<String, List<Print>> register = this.getMappedPrints();
        List<List<Print>> matches = new LinkedList<>();
        if (keyPrefix != null) {
            keyPrefix = keyPrefix.toLowerCase();
            // Find keys that are equal-to or greater-than the prefix.
            SortedMap<String, List<Print>> tail = register.tailMap(keyPrefix);
            Iterator<String> it = tail.keySet().iterator();
            // Stop when we find a mismatch.
            boolean endOfSearch = false;
            while (!endOfSearch && it.hasNext()) {
                String key = it.next();
                if (key.startsWith(keyPrefix)) {
                    matches.add(register.get(key));
                } else {
                    endOfSearch = true;
                }
            }
        }
        List<Print> results = new ArrayList<>();
        for (List<Print> matchedList : matches) {
            results.addAll(matchedList);
        }
        return results;
    }

    /**
     * Checks whether or not there is s print in the register
     * matching the name and publisher of the specified print.
     *
     * @param title     The title of the print to check for.
     * @param publisher The publisher of the print to check for.
     * @return Returns true if there already is such a print, else false.
     */
    private boolean existsInRegister(String title, String publisher) {
        boolean exists = false;
        Iterator<Print> it = this.getPrintsInRegister();
        while (it.hasNext() && !exists) {
            Print p = it.next();
            if ((p.getTitle().toLowerCase().equals(title.toLowerCase()))
                    && (p.getPublisher().toLowerCase().equals(publisher.toLowerCase()))) {
                exists = true;
            }
        }
        return exists;
    }


    /**
     * Returns a sorted map, where all the register's prints
     * name and publisher are mapped to the print itself.
     *
     * @return Returns a sorted map, where all the register's prints
     * name and publisher are mapped to the print itself.
     */
    private SortedMap<String, List<Print>> getMappedPrints() {
        SortedMap<String, List<Print>> returnMap = new TreeMap<>();
        Iterator<Print> it = this.getPrintsInRegister();
        while (it.hasNext()) {
            Print p = it.next();
            String titleKey = p.getTitle().toLowerCase();
            String publisherKey = p.getPublisher().toLowerCase();

            if (!returnMap.containsKey(titleKey)) {
                returnMap.put(titleKey, new ArrayList<>(Collections.singleton(p)));
            } else {
                List<Print> tempList = returnMap.get(titleKey);
                tempList.add(p);
                returnMap.put(titleKey, tempList);
            }

            if (!returnMap.containsKey(publisherKey)) {
                returnMap.put(publisherKey, new ArrayList<>(Collections.singleton(p)));
            } else {
                List<Print> tempList = returnMap.get(publisherKey);
                tempList.add(p);
                returnMap.put(publisherKey, tempList);
            }
        }
        return returnMap;
    }
}

