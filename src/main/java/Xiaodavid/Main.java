package Xiaodavid;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Xiaodavid xiaodavid = new Xiaodavid("tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            MainWindow controller = fxmlLoader.getController();
            controller.setXiaodavid(xiaodavid);  // inject the Duke instance

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
