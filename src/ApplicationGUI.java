import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Iterator;

public class ApplicationGUI extends Application {

    private final Register printRegister = new Register();
    private TextArea mainWindow;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane rootNode = new BorderPane();
//monstrosity
        System.out.println("he");
        
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

        mainWindow = new TextArea();
        mainWindow.setEditable(false);

        rootNode.setCenter(mainWindow);
        rootNode.setLeft(buttonMenu);
        rootNode.getLeft().setId("leftBar");

        listBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listAllPrints(mainWindow);
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
    private void listAllPrints(TextArea window)
    {
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
        window.setText(returnString);
    }

    private void addDemoBooks() {
        this.printRegister.addPrintToRegister(new Book("Harry Potter", "Continium", "J.K Rowling", "1", "24.10.1998"));
        this.printRegister.addPrintToRegister(new Periodical("Vogue", "Conde Nast", "Fashion", "Magazine", "12"));
    }
}
