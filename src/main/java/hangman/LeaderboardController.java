package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LeaderboardController {

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> usernameColumn;

    @FXML
    private TableColumn<?, ?> totalWinsColumn;

    @FXML
    void moveMenuPage(ActionEvent event) {

    }

}
