package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_userid;

    @FXML
    private Button buttonsignup;

    @FXML
    private Button buttonlogin;
    private DatabaseManager databaseManager;
    public SignUpController() throws SQLException {
        databaseManager = new DatabaseManager();
    }

    @FXML
    void moveTologin(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogedIn.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setTitle("LogIn");
        stage.setScene(new Scene(parent));
        stage.show();

    }
    @FXML
    public void handleSignUp(ActionEvent event) throws IOException {
        String username = tf_username.getText();
        String password = tf_password.getText();
        String name = tf_name.getText();
        int userId = Integer.parseInt(tf_userid.getText());

        if (databaseManager.isUsernameTaken(username)) {
            //System.out.println("Signup failed: Username already taken");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Signup failed: Username already taken");
            alert.show();
        } else {
            databaseManager.signupUser(username, password, name,userId);
            System.out.println("Signup successful");

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent parent = fxmlLoader.load();
            stage.setTitle("Menu");
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

}
