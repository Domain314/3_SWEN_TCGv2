package at.domain314.models.users;

import at.domain314.models.cards.Card;
import at.domain314.models.cards.Collection;
import at.domain314.models.cards.Deck;
import at.domain314.utils.Constants;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Player {
    int id;
    String name;
    String bio;
    String image;
    int credits;
    List<String> deckIDs;
    List<String>  stackIDs;
    int gamesCounter;
    int winCounter;
    int elo;
    Deck deck;
    Collection stack;

    public Player() {}
    public Player(int id, String name, String bio, String image, int credits, Array deck, Array stack, int gamesCounter, int winCounter, int elo) {
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
    //  public Collection getStack() { return stack; }                    TO DELETE

    public void setID(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBio(String bio) { this.bio = bio; }
    public void setImage(String image) { this.image = image; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setElo(int elo) { this.elo = elo; }
    public void setGamesCounter(int gamesCounter) { this.gamesCounter = gamesCounter; }
    public void setWinCounter(int winCounter) { this.winCounter = winCounter; }
    public void setDeckIDs(List<String> deckIDs) { this.deckIDs = deckIDs; }
    public void setStackIDs(List<String> stackIDs) { this.stackIDs = stackIDs; }
    public void setDeck(Deck deck) { this.deck = deck; }
    //  public void setStack(Collection stack) { this.stack = stack; }    TO DELETE

    public void changeElo(int eloAmount) { elo += eloAmount; }

//  End game after losing (change elo, increment games counter and prepareCardIDs)
    public void endGame(int eloAmount, Deck enemyDeck) {
        changeElo(eloAmount);
        gamesCounter++;
        prepareCardIDs(enemyDeck);
    }
//  End game after winning (change elo, increment games, win counter and prepareCardIDs)
    public void endGame(int eloAmount, boolean win) {
        changeElo(eloAmount);
        gamesCounter++;
        if (win) { winCounter++; }
        prepareCardIDs();
    }

//  Prepare deckIDs and stackIDs for db-update, after a battle.
//  Player won
    public void prepareCardIDs() {
        List<String> newStack = new ArrayList<>(this.stackIDs);
//        for (String cardID : this.stackIDs) {
//            newStack.add(cardID);
//        }
        finalizeCardIDs(newStack);
    }

//  Player lost
    public void prepareCardIDs(Deck enemyDeck) {
        List<String> newStack = new ArrayList<>();
        for (String cardID : this.stackIDs) {
            if (enemyDeck.isCardInCollection(cardID)) { continue; }
            newStack.add(cardID);
        }
        finalizeCardIDs(newStack);
    }
//  Add new cards from the enemy, which where obtained in battle (win + draw)
    private void finalizeCardIDs(List<String> newStack) {
        for (Card card : this.deck.getCards()) {
            if (this.deck.isCardInList(card.getID(), newStack)) { continue; }
            newStack.add(card.getID());
        }
        this.stackIDs = newStack;
        this.deckIDs = new ArrayList<>();
    }
}
