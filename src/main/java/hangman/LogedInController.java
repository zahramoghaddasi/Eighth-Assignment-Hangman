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
    //private String userName = tf_username.getText();

    public LogedInController() throws SQLException {
        databaseManager = new DatabaseManager();
    }

//    public String getUserName(){
//        return userName;
//    }
    @FXML
    void moveTosignup(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Image icon = new Image("file:///C:/Users/Click/Desktop/AP/eighth/Eighth-Assignment-Hangman/src/main/resources/hangman/icons8-hangman-50.png");
        Parent parent = fxmlLoader.load();
        stage.getIcons().add(icon);
        stage.setTitle("SignUp");
        stage.setScene(new Scene(parent,700,700));
        stage.show();

    }
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String username = tf_username.getText();
        String password = tf_password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields must be filled", "Please enter both username and password.");
            return;
        }

        if (databaseManager.isUserExist(username, password)) {
            UserSession.getInstance().setUsername(username);
            loadGame(username);
        } else {
            showAlert("Error", "Login failed", "Username does not exist or password is incorrect.");
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
        System.out.println("Login successful");
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Image icon = new Image("file:///C:/Users/Click/Desktop/AP/eighth/Eighth-Assignment-Hangman/src/main/resources/hangman/icons8-hangman-50.png");
        Parent parent = fxmlLoader.load();
        stage.getIcons().add(icon);
        stage.setTitle("Menu");
        stage.setScene(new Scene(parent,700,700));
        stage.show();
    }

}
