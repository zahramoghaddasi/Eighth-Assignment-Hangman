module hangman {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens hang_man.hangman to javafx.fxml;
    exports hangman;
}