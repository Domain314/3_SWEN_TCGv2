package at.domain314.models.overseer;

import at.domain314.game.Game;
import at.domain314.models.cards.Card;
import at.domain314.models.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Overseer {
    static List<Card> allCards = new ArrayList<>();
    static Map<String,Card> allCardsMap = new HashMap<>();
    static List<User> allUsers = new ArrayList<>();
    static Map<Integer,User> allUsersMap = new HashMap<>();
    static List<Game> allGames = new ArrayList<>();
    static Map<Integer,Game> allGamesMap = new HashMap<>();

    public static List<Card> getAllCards() { return allCards; }
    public static Card getCard(int id) { return allCardsMap.get(id); }
    public static List<User> getAllUsers() { return allUsers; }
    public static User getUser(int id) { return allUsersMap.get(id); }
    public static List<Game> getAllGames() { return allGames; }
    public static Game getGame(int id) { return allGamesMap.get(id); }

    public static void addCard(Card card) {
        allCards.add(card);
        allCardsMap.put(card.getID(), card);
    }

    public static void addUser(User user) {
        allUsers.add(user);
        allUsersMap.put(user.getID(), user);
    }

    public static void addGame(Game game) {
        allGames.add(game);
        allGamesMap.put(game.getID(), game);
    }
}
