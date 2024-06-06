package hangman;

public class GameInfo {
    private String gameID;
    private String username;
    private String word;
    private int wrongGuesses;
    private int time;
    private boolean win;
    public GameInfo(String gameID,String username,String word,int wrongGuesses,int time,boolean win){
        this.gameID = gameID;
        this.time = time;
        this.wrongGuesses = wrongGuesses;
        this.win = win;
        this.word = word;
        this.username = username;
    }
    public void setWrongGuesses(int wrongGuesses){
            this.wrongGuesses = wrongGuesses;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setWord(String word){
        this.word = word;
    }
    public void setTime(int time){
        this.time = time;
    }
    public void setGameID(String gameID){
        this.gameID = gameID;
    }
    public void setWin(boolean win){
        this.win = win;
    }
    public int getWrongGuesses(){
        return wrongGuesses;
    }
    public String getUsername(){
        return username;
    }
    public String getWord(){
        return word;
    }
    public int getTime(){
       return time;
    }
    public String getGameID(){
        return gameID;
    }
    public boolean isWin(){
        return win;
    }


}
