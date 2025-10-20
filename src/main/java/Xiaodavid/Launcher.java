package Xiaodavid;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    /**
     * Delegates to {@link Application#launch} to start the JavaFX application.
     *
     * @param args command-line arguments passed to the program
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
