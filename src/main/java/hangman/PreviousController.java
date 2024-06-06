package hangman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PreviousController implements Initializable {

    @FXML
    private TableView<GameInfo> table;

    @FXML
    private TableColumn<GameInfo, String> gameIDColumn;

    @FXML
    private TableColumn<GameInfo, String> wordColumn;

    @FXML
    private TableColumn<GameInfo, Integer> wrongGuessesColumn;

    @FXML
    private TableColumn<GameInfo, Integer> timeColumn;

    @FXML
    private TableColumn<GameInfo, Boolean> winColumn;

    @FXML
    void moveMenuPage(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Image icon = new Image("file:///C:/Users/Click/Desktop/AP/eighth/Eighth-Assignment-Hangman/src/main/resources/hangman/icons8-hangman-50.png");
        Parent parent = fxmlLoader.load();
        stage.getIcons().add(icon);
        stage.setTitle("Menu");
        stage.setScene(new Scene(parent,700,700));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameIDColumn.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        winColumn.setCellValueFactory(new PropertyValueFactory<>("win"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        wrongGuessesColumn.setCellValueFactory(new PropertyValueFactory<>("wrongGuesses"));
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));

        String username = UserSession.getInstance().getUsername();
        try {
            List<GameInfo> gameInfoList = DatabaseManager.getInstance().getUserGameInfo(username);
            ObservableList<GameInfo> gameInfos = FXCollections.observableArrayList(gameInfoList);
            table.setItems(gameInfos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
