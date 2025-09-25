package Xiaodavid;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private Xiaodavid xiaodavid;

    private Image gigaChadImage = new Image(this.getClass().getResourceAsStream("/images/Gigachad.png"));
    private Image davidImage = new Image(this.getClass().getResourceAsStream("/images/David.png"));

    /** Initializes the controller. */
    @FXML
    public void initialize() {
        // Auto-scroll to the bottom when new messages are added
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Xiaodavid instance and shows the welcome message. */
    public void setXiaodavid(Xiaodavid xiaodavid) {
        this.xiaodavid = xiaodavid;

        // Show welcome message at startup
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(xiaodavid.getWelcomeMessage(), davidImage)
        );
    }

    /**
     * Handles user input: shows user dialog, Duke response, and clears input.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isEmpty()) return;

        String response = xiaodavid.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, gigaChadImage),
                DialogBox.getDukeDialog(response, davidImage)
        );

        userInput.clear();
    }
}
