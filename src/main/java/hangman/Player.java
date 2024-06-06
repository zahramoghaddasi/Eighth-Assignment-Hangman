package hangman;

public class Player {
    private String username;
    private int totalWins;

    public Player(String username , int totalWins ){
        this.username = username;
        this.totalWins = totalWins;
    }
    public String getUsername(){
        return username;
    }
    public int getTotalWins(){
        return totalWins;
    }
}
