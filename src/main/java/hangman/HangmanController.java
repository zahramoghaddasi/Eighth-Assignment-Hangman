package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private boolean gameEnded = false;

    private ArrayList<String> hangManLives = new ArrayList<>(Arrays.asList(
            """
            +---+
            |       |
                    |
                    |
                    |
         =====""",
            """
            +---+
            |       |
           O      |
                    |
                    |
         =====""",
            """
            +---+
            |       |
           O      |
            |       |
                    |
         =====""",
            """
            +---+
            |       |
           O      |
          /|       |
                    |
         =====""",
            """
            +---+
            |       |
           O      |
          /|\\      |
                    |
         =====""",
            """
            +---+
            |       |
           O      |
          /|\\      |
          /         |
         =====""",
            """
            +---+
            |       |
           O      |
          /|\\      |
          / \\      |
         ====="""
    ));

    @FXML
    void handleLetter(ActionEvent event) {
        if (!gameEnded) {
            Button button = (Button) event.getSource();
            String letter = button.getText();
            button.setDisable(true);
            playTurn(letter);
        }
    }
    private void playTurn(String guess) {
        if (word == null) {
            return;  // Word must be set before playing
        }
        if (gameEnded) {
            return; // اگر بازی به پایان رسیده، خروج کن
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
                gameEnded = true;
                disableAllButtons();
            }
        } else {
            hangmanTextArea.setText(hangManLives.get(++livesPos));
            if (livesPos == hangManLives.size() - 1) {
                endOfGameText.setText("You LOST!! The word was: " + word);
                gameEnded = true;
                disableAllButtons();
            }
        }
    }

    private void setupWord(String word) {
        secretWord.setLength(0);
        secretWord.append("-".repeat(word.length()));
        textForWord.setText(secretWord.toString().replaceAll(".", "_ "));
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
        // Send HTTP GET request to fetch list of words from the website
        try {
            URL url = new URL("https://api-ninjas.com/api/animals");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", "HwA0+AKJLZhGKrsw3JwnxQ==WHcRZYxbqRdQq8qa");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON response to get the list of words
            JSONArray wordsArray = new JSONArray(response.toString());

            // Select a random word from the list
            int randomIndex = (int) (Math.random() * wordsArray.length());
            word = wordsArray.getString(randomIndex).toUpperCase();

            // Set up the word for the game
            setupWord(word);
            wordInput.clear();
            wordInput.setDisable(true);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            endOfGameText.setText("Failed to fetch word from the website.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hangmanTextArea.setText(hangManLives.get(livesPos));
    }
}