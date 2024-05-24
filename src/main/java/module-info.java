module hang_man.hangman {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens hang_man.hangman to javafx.fxml;
    exports hang_man.hangman;
}