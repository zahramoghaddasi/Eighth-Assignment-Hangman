package hangman;

import java.sql.*;

// Use JDBC to connect to your database and run queries

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "311384";
    private static Connection connection;

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
}
