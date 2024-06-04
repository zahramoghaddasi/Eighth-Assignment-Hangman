package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HangmanController implements Initializable {

    @FXML
    private Label hangmanTextArea;

    @FXML
    private TextField wordInput;

    @FXML
    private Button setword;

    @FXML
    private Label endOfGameText;
    @FXML
    private Label textForWord;
    private String word;

    private StringBuilder secretWord = new StringBuilder();

    private int livesPos = 0;

    private ArrayList<String> hangManLives = new ArrayList<>(Arrays.asList(
            """
            +---+
            |   |
                |
                |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
                |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
            |   |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
           /|   |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
           /|\\  |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
           /|\\  |
           /    |
                |
          =========""",
            """
            +---+
            |   |
            O   |
           /|\\  |
           / \\  |
                |
          ========="""
    ));

    @FXML
    void handleLetter(ActionEvent event) {
        Button button = (Button) event.getSource();
        String letter = button.getText();
        button.setDisable(true);
        playTurn(letter);
    }
    private void playTurn(String guess) {
        if (word == null) {
            return;  // Word must be set before playing
        }

        ArrayList<Integer> positions = new ArrayList<>();
        char[] wordChars = word.toCharArray();
        char letterGuess = guess.charAt(0);

        if (word.contains(guess)) {
            for (int i = 0; i < word.length(); i++) {
                if (wordChars[i] == letterGuess) {
                    positions.add(i);
                }
            }
            for (int pos : positions) {
                secretWord.setCharAt(pos, letterGuess);
            }
            textForWord.setText(secretWord.toString().replaceAll(".", "$0 "));
            if (secretWord.toString().equals(word)) {
                endOfGameText.setText("You won!!");
                disableAllButtons();
            }
        } else {
            hangmanTextArea.setText(hangManLives.get(++livesPos));
            if (livesPos == hangManLives.size() - 1) {
                endOfGameText.setText("You LOST!! The word was: " + word);
                disableAllButtons();
            }
        }
    }

    private void setupWord() {
        secretWord.setLength(0);
        secretWord.append("*".repeat(word.length()));
        textForWord.setText(secretWord.toString().replaceAll(".", "$0 "));
    }

    private void disableAllButtons() {
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            Button button = (Button) hangmanTextArea.getScene().lookup("#" + letter);
            if (button != null) {
                button.setDisable(true);
            }
        }
    }


    @FXML
    void reset(ActionEvent event) {
        word = null;
        secretWord.setLength(0);
        livesPos = 0;
        hangmanTextArea.setText(hangManLives.get(0));
        endOfGameText.setText("");
        wordInput.setDisable(false);
        wordInput.clear();
        textForWord.setText("");
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            Button button = (Button) hangmanTextArea.getScene().lookup("#" + letter);
            if (button != null) {
                button.setDisable(false);
            }
        }

    }

    @FXML
    void setWord(ActionEvent event) {
        word = wordInput.getText().toUpperCase();
        if (word != null && !word.isEmpty()) {
            setupWord();
            wordInput.clear();
            wordInput.setDisable(true);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hangmanTextArea.setText(hangManLives.get(livesPos));
    }
}
