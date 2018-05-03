import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.List;

public class ApplicationGUI extends Application {

    private final Register printRegister = new Register();
    private final Pane mainWindow = new StackPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainWindow.setId("main");

        BorderPane rootNode = new BorderPane();

        addDemoBooks();

        VBox buttonMenu = new VBox();
        buttonMenu.setPrefWidth(150);
        Button addBtn = new Button("Add Print");
        addBtn.setPrefWidth(buttonMenu.getPrefWidth());
        Button searchBtn = new Button("Search");
        searchBtn.setPrefWidth(buttonMenu.getPrefWidth());
        Button listBtn = new Button("List All");
        listBtn.setPrefWidth(buttonMenu.getPrefWidth());
        Button convertBtn = new Button("Book To Series");
        convertBtn.setPrefWidth(buttonMenu.getPrefWidth());
        Button removeBtn = new Button("Remove Print");
        removeBtn.setPrefWidth(buttonMenu.getPrefWidth());
        buttonMenu.getChildren().addAll(addBtn, listBtn, searchBtn, convertBtn, removeBtn);
        buttonMenu.setSpacing(10);

        rootNode.setCenter(mainWindow);
        rootNode.setLeft(buttonMenu);
        rootNode.getLeft().setId("leftBar");

        listBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listAllPrints(mainWindow);
            }
        });

        searchBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                search();
            }
        });

        convertBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addBookToSeries();
            }
        });

        Scene scene = new Scene(rootNode, 1000, 600);
        scene.getStylesheets().add(ApplicationGUI.class.getResource("ApplicationGUI.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Prints a overview of all the books in the register to the specified textArea.
     * @param window The specified text area to print to.
     */
    private void listAllPrints(Pane window)
    {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        String returnString = "";
        Iterator<Print> it = this.printRegister.getPrintsInRegister();
        if (!it.hasNext()) {
            returnString = "No books in register.";
        }
        else {
            while (it.hasNext()) {
                Print p = it.next();
                returnString += p.getTitle() + " (" + p.getPublisher() + ")" + "\n";
            }
        }
        textArea.setText(returnString);
        setMainWindow(textArea);
    }

    /**
     * Clears the content of the main window.
     */
    private void setMainWindow(Parent window) {
        this.mainWindow.getChildren().clear();
        this.mainWindow.getChildren().add(window);
    }

    /**
     * Allows the user to do searches for books by title and publisher.
     * When the user presses the "search" button, the "engine" takes the search
     * and will display any results.
     * If there are no results, "No such book(s) in register" id displayed".
     */
    private void search() {
        GridPane grid = new GridPane();
        Label key = new Label("Search:");
        TextField searchField = new TextField();
        Button enter = new Button("Search");

        enter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                List<Print> searchResults = printRegister.search(searchField.getText());
                String returnString = "";
                if (searchResults.isEmpty()) {
                    returnString = "No such book(s) in the register.";
                }
                else {
                    for (Print p : searchResults) {
                        returnString += p.getTitle() + " (" + p.getPublisher() + ")" + "\n";
                    }
                }
                TextArea textArea = new TextArea();
                textArea.setEditable(false);
                textArea.setText(returnString);
                setMainWindow(textArea);
            }
        });

        grid.add(key, 0, 0, 1, 1);
        grid.add(searchField, 1, 0, 2, 1);
        grid.add(enter, 2, 1);
        setMainWindow(grid);
    }

    private void addBookToSeries() {
        GridPane grid = new GridPane();
        Label title = new Label("Book title: ");
        Label series = new Label("Series: ");
        TextField titleKey = new TextField();
        TextField seriesKey = new TextField();
        Button addBtn = new Button("Add Book");
        grid.add(title, 0, 0, 1, 1);
        grid.add(titleKey, 1, 0, 2, 1);
        grid.add(series, 0, 1, 1, 1);
        grid.add(seriesKey, 1, 1, 2, 1);
        grid.add(addBtn, 1, 2);

        addBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Text text = new Text();
                boolean matchBook = false;
                boolean matchSeries = false;
                Book resultBook = null;
                BookSeries resultSeries = null;
                Iterator<Print> it = printRegister.getPrintsInRegister();
                while ((!matchBook || !matchSeries) && it.hasNext()) {
                    Print p = it.next();
                    String formattedKey = p.getTitle().toLowerCase();
                    if (formattedKey.equals(titleKey.getText().toLowerCase()) ||
                            formattedKey.equals(seriesKey.getText().toLowerCase())) {
                        if (p instanceof Book) {
                            matchBook = true;
                            resultBook = (Book) p;
                        }
                        else if (p instanceof BookSeries) {
                            matchSeries = true;
                            resultSeries = (BookSeries) p;
                        }
                    }
                }
                if (resultBook == null || resultSeries == null) {
                    text.setText("Oops: Either title and/or series does not exist in the register \n"
                    + "Please try again.");
                }
                else {
                    boolean added = resultSeries.addBook(resultBook);
                    if (added) {
                        text.setText(resultBook.getTitle() + " was successfully added to " + resultSeries.getTitle());
                    }
                    else {
                        text.setText("Oops: " + resultBook.getTitle() + " already exists in " + resultSeries.getTitle());
                    }
                }
                grid.add(text, 1, 3);
            }
        });
        setMainWindow(grid);
    }

    private void addDemoBooks() {
        this.printRegister.addPrintToRegister(new Book("Harry Potter", "Continium", "J.K Rowling", "1", "24.10.1998"));
        this.printRegister.addPrintToRegister(new Periodical("Vogue", "Conde Nast", "Fashion", "Magazine", "12"));
        this.printRegister.addPrintToRegister(new BookSeries("Bahuba", "Bahuba", "Bahuba"));
    }
}
