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
        stage.setScene(new Scene(parent,700,700));
        stage.show();

    }
    @FXML
    public void handleSignUp(ActionEvent event) throws IOException {
        String username = tf_username.getText();
        String password = tf_password.getText();
        String name = tf_name.getText();
        String userIdText = tf_userid.getText();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || userIdText.isEmpty()) {
            showAlert("Error", "All fields must be filled", "Please enter username, password, name, and user ID.");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid User ID", "User ID must be a number.");
            return;
        }

        if (databaseManager.isUsernameTaken(username)) {
            showAlert("Error", "Signup failed", "Username already taken.");
        } else {
            UserSession.getInstance().setUsername(username);
            databaseManager.signupUser(username, password, name,userId);
            loadGame(username);
            System.out.println("Signup successful");


        }
    }
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void loadGame(String username) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent parent = fxmlLoader.load();

        stage.setTitle("Menu");
        stage.setScene(new Scene(parent,700,700));
        stage.show();
    }

}
