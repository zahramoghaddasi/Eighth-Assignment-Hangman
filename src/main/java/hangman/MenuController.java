package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonprevious;

    @FXML
    private Button buttonleaderboard;

    @FXML
    private Button buttonexit;

    @FXML
    private void Handleoption1(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hangman-view.fxml"));
        Image icon = new Image("file:///C:/Users/Click/Desktop/AP/eighth/Eighth-Assignment-Hangman/src/main/resources/hangman/icons8-hangman-50.png");
        Parent parent = fxmlLoader.load();
        stage.getIcons().add(icon);
        //Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hangman Game");
        stage.setScene(new Scene(parent,700,700));
        stage.show();
    }
    @FXML
    private void Handleoption4(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogedIn.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setTitle("LogIn");
        stage.setScene(new Scene(parent,700,700));
        stage.show();
    }

}
