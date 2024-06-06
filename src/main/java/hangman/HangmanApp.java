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
        Image icon = new Image("file:///C:/Users/Click/Desktop/AP/eighth/Eighth-Assignment-Hangman/src/main/resources/hangman/icons8-hangman-50.png");
        stage.setTitle("LOgin");
        stage.getIcons().add(icon);
        stage.setScene(new Scene(root,700,700));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}