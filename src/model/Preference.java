package model;

public class Preference {
    private int id;
    private String rank;
    private boolean preferedVoiceChat;
    private String preferedGame;

    public boolean matches(Preference other) {
        if (other == null) return false;
        if (this.preferedGame != null && !this.preferedGame.equalsIgnoreCase(other.preferedGame)) {
            return false;
        }
        if (this.preferedVoiceChat != other.preferedVoiceChat) return false;
        if (this.rank != null && other.rank != null) {
            return this.rank.equalsIgnoreCase(other.rank);
        }
        return true;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }
    public boolean isPreferedVoiceChat() { return preferedVoiceChat; }
    public void setPreferedVoiceChat(boolean preferedVoiceChat) { this.preferedVoiceChat = preferedVoiceChat; }
    public String getPreferedGame() { return preferedGame; }
    public void setPreferedGame(String preferedGame) { this.preferedGame = preferedGame; }
}

