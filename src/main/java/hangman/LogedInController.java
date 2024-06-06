package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LogedInController {

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private Button buttonlogin;

    @FXML
    private Button buttonsignup;
    private DatabaseManager databaseManager;

    public LogedInController() throws SQLException {
        databaseManager = new DatabaseManager();
    }

    @FXML
    void moveTosignup(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setTitle("SignUp");
        stage.setScene(new Scene(parent));
        stage.show();

    }
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String username = tf_username.getText();
        String password = tf_password.getText();

        if (databaseManager.isUserExist(username, password)) {
            System.out.println("Login successful");
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent parent = fxmlLoader.load();
            stage.setTitle("Menu");
            stage.setScene(new Scene(parent));
            stage.show();
        } else {
            //System.out.println("Login failed: Username does not exist or password is incorrect");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Login failed: Username does not exist or password is incorrect");
            alert.show();
        }
    }

}
