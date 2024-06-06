package hangman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HangmanApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogedIn.fxml"));
        stage.setTitle("LOgin");
        stage.setScene(new Scene(root,700,700));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}