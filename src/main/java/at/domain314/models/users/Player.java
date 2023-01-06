package at.domain314.models.users;

import at.domain314.models.cards.Collection;
import at.domain314.models.cards.Deck;
import at.domain314.utils.Constants;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    int id;
    String name;
    String bio;
    String image;
    int credits;
    List<String> deckIDs;
    List<String>  stackIDs;// = new ArrayList<>();
    int gamesCounter;
    int winCounter;
    int elo;
    Deck deck;
    Collection stack;

    public Player() {}
    public Player(int id, String name, String bio, String image, int credits, Array deck, Array stack, int gamesCounter, int winCounter, int elo) {
        try {this.id = id;
            this.name = name;
            this.bio = bio;
            this.image = image;
            this.credits = credits;
            this.gamesCounter = gamesCounter;
            this.winCounter = winCounter;
            this.elo = elo;
            deckIDs = Constants.convertArrayToList(deck);
            stackIDs = Constants.convertArrayToList(stack);} catch (Exception e) { System.out.println(e.getMessage()); }

    }

    public void setAll(int id, String name, String bio, String image, int credits, Array deck, Array stack, int gamesCounter, int winCounter, int elo) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.image = image;
        this.credits = credits;
        this.gamesCounter = gamesCounter;
        this.winCounter = winCounter;
        this.elo = elo;
        deckIDs = Constants.convertArrayToList(deck);
        stackIDs = Constants.convertArrayToList(stack);
    }

    public int getID() { return id; }
    public String getName() { return name; }
    public String getBio() { return bio; }
    public String getImage() { return image; }
    public int getCredits() { return credits; }
    public int getElo() { return elo; }
    public int getGamesCounter() { return gamesCounter; }
    public int getWinCounter() { return winCounter; }
    public List<String> getDeckIDs() { return deckIDs; }
    public List<String> getStackIDs() { return stackIDs; }
    public Deck getDeck() { return deck; }
    public Collection getStack() { return stack; }

    public void setName(String name) { this.name = name; }
    public void setBio(String bio) { this.bio = bio; }
    public void setImage(String image) { this.image = image; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setElo(int elo) { this.elo = elo; }
    public void setGamesCounter(int gamesCounter) { this.gamesCounter = gamesCounter; }
    public void setWinCounter(int winCounter) { this.winCounter = winCounter; }
    public void setDeck(Deck deck) { this.deck = deck; }
    public void setStack(Collection stack) { this.stack = stack; }

    //    return false, if not enough credits, to subtract.
    public boolean changeCredits(int amount) {
        if (credits + amount < 0) { return false; }
        credits += amount;
        return true;
    }

    public void changeElo(int eloAmount) { elo += eloAmount; }

    //    End game after losing (change elo and increment games counter)
    public void endGame(int eloAmount) {
        changeElo(eloAmount);
        gamesCounter++;
    }
    //    End game after winning (change elo, increment games and win counter)
    public void endGame(int eloAmount, boolean win) {
        changeElo(eloAmount);
        gamesCounter++;
        if (win) {
            winCounter++;
        }
    }
}
