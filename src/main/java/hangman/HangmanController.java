package hangman;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.util.Duration;
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
    @FXML
    private Label timerLabel;
    private String word;
    private Timeline timeline;
    private int secondsElapsed = 0;

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

    private DatabaseManager databaseManager;
    private String username;

    public HangmanController() throws SQLException {
        databaseManager = new DatabaseManager();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    void handleLetter(ActionEvent event) {
        if (!gameEnded) {
            Button button = (Button) event.getSource();
            String letter = button.getText();
           // button.setDisable(true);
            button.setOpacity(0.5);
            playTurn(letter);
        }
    }
    private void playTurn(String guess) {
        if (word == null || gameEnded) {
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
                gameEnded = true;
                disableAllButtons();
                stopTimer();
                saveGameInfo(true);
            }
        } else {
            hangmanTextArea.setText(hangManLives.get(++livesPos));
            if (livesPos == hangManLives.size() - 1) {
                endOfGameText.setText("You LOST!! The word was: " + word);
                gameEnded = true;
                disableAllButtons();
                stopTimer();
                saveGameInfo(false);
            }
        }
    }

    private void saveGameInfo(boolean win) {
        String gameID = generateGameID();
        int wrongGuesses = livesPos;
        int time = secondsElapsed;

        String username = UserSession.getInstance().getUsername();

        databaseManager.insertGameInfo(gameID, username, word, wrongGuesses, time, win);
    }

    private String generateGameID() {
        return java.util.UUID.randomUUID().toString();
    }

    private void setupWord(String word) {
        secretWord.setLength(0);
        secretWord.append("-".repeat(word.length()));
        textForWord.setText(secretWord.toString().replaceAll(".", "_ "));
        startTimer();
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
        if(word != null){
            saveGameInfo(false);
        }
        word = null;
        secretWord.setLength(0);
        livesPos = 0;
        gameEnded = false;
        hangmanTextArea.setText(hangManLives.get(0));
        endOfGameText.setText("");
        wordInput.setDisable(false);
        wordInput.clear();
        textForWord.setText("");
        stopTimer();
        secondsElapsed = 0;
        updateTimerLabel();
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            Button button = (Button) hangmanTextArea.getScene().lookup("#" + letter);
            if (button != null) {
                button.setDisable(false);
                button.setOpacity(1.0);
            }
        }

    }

    @FXML
    void setWord(ActionEvent event) {
        String[] allLetters ={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"} ;
        Random rnd = new Random();
        String random = allLetters[rnd.nextInt(allLetters.length)];
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/animals?name="+random);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", "HwA0+AKJLZhGKrsw3JwnxQ==WHcRZYxbqRdQq8qa");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(connection.getInputStream());
           // System.out.println(root.path("fact").asText());
            //System.out.println(root.get(0));

            if (root.isArray() && root.size() > 0) {
                JsonNode cheetahNode = root.get(0);
                word = cheetahNode.path("name").asText().toUpperCase();
                System.out.println(word);
                setupWord(word);
                wordInput.setDisable(true);
            } else {
                endOfGameText.setText("Failed to fetch word. Try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            endOfGameText.setText("Failed to fetch word. Try again.");
        }
    }

    private void startTimer() {
        secondsElapsed = 0;
        updateTimerLabel();
        timeline.playFromStart();
    }

    private void stopTimer() {
        timeline.stop();
    }

    private void updateTimerLabel() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }
    private void setupTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsElapsed++;
            updateTimerLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hangmanTextArea.setText(hangManLives.get(livesPos));
        setupTimer();
    }
    @FXML
    private void Handleexit(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setTitle("Menu");
        stage.setScene(new Scene(parent,700,700));
        stage.show();
    }
}