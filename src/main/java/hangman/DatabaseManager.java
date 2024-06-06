package hangman;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Use JDBC to connect to your database and run queries

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "311384";
    private static Connection connection;
    private static DatabaseManager instance;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(URL,USER,PASSWORD);
    }
    public boolean isUserExist(String usename , String password){
        String selectSql = "SELECT COUNT(*) AS user_count FROM public.UserInfo WHERE Username = ? AND Password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)){
            preparedStatement.setString(1,usename);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt("user_count");
                return count > 0;

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameTaken(String username){
        String selectSql = "SELECT COUNT(*) AS user_count FROM public.UserInfo WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)){
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int count = resultSet.getInt("user_count");
                return count > 0;

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void signupUser(String username, String password, String name , int userID) {
        String insertSql = "INSERT INTO public.UserInfo (Name, Username, Password, UserId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4,userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertGameInfo(String gameID, String username, String word, int wrongGuesses, int time, boolean win) {
        String insertSql = "INSERT INTO public.GameInfo (GameID, Username, Word, WrongGuesses, Time, Win) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, gameID);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, word);
            preparedStatement.setInt(4, wrongGuesses);
            preparedStatement.setInt(5, time);
            preparedStatement.setBoolean(6, win);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static DatabaseManager getInstance() throws SQLException {

        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    public List<Player> getLeaderboard() throws SQLException {
        String selectSql = "SELECT Username, COUNT(Win) AS TotalWins FROM public.GameInfo WHERE Win = true GROUP BY Username ORDER BY TotalWins DESC";
        List<Player> leaderboard = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                int totalWins = resultSet.getInt("TotalWins");
                leaderboard.add(new Player(username, totalWins));
            }
        }
        return leaderboard;
    }
    public void printAllGameInfo() {
        String selectSql = "SELECT * FROM public.GameInfo";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String gameID = resultSet.getString("GameID");
                String username = resultSet.getString("Username");
                String word = resultSet.getString("Word");
                int wrongGuesses = resultSet.getInt("WrongGuesses");
                int time = resultSet.getInt("Time");
                boolean win = resultSet.getBoolean("Win");

                System.out.println("GameID: " + gameID);
                System.out.println("Username: " + username);
                System.out.println("Word: " + word);
                System.out.println("Wrong Guesses: " + wrongGuesses);
                System.out.println("Time: " + time);
                System.out.println("Win: " + win);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.printAllGameInfo();
    }

}
