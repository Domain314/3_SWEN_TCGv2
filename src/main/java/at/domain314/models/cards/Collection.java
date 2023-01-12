package at.domain314.models.cards;

import at.domain314.utils.Constants;

import java.util.ArrayList;
import java.util.List;

abstract public class Collection {
    List<Card> cards = new ArrayList<>();

    public Collection() { }

    public List<Card> getCards() { return cards; }

//    Add one Card or List of Cards.
    public void addCard(Card card) {
        this.cards.add(card);
    }
    public void addCard(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            this.cards.add(cards.get(i));
        }
    }

//    Make a List<String> of the best cards
    public List<String> getBestCards(int amount) {
        if (this.cards == null) return null;
        if (this.cards.size() < amount) return null;

        List<String> bestCards = new ArrayList<>();
        for (int a = 0; a < amount; a++) {
            int x = 0;
            Card bestCard = this.cards.get(x);
            while (isCardInList(bestCard.getID(), bestCards)) {
                bestCard = this.cards.get(++x);
            }
            for (Card card : this.cards) {
                if (isCardInList(card.getID(), bestCards)) continue;
                if (card.getDamage() > bestCard.getDamage()) {
                    bestCard = card;
                }
            }
            bestCards.add(bestCard.getID());
        }
        return bestCards;
    }

//    Make a List<String> of random cards
    public List<String> getRandomCards(int amount) {
        if (this.cards == null) return null;
        if (this.cards.size() < amount) return null;

        List<String> randomCards = new ArrayList<>();
        while (randomCards.size() < amount) {
            String randomCardID;
            do  {
                randomCardID = this.cards.get(Constants.RANDOM.nextInt(0, this.cards.size())).getID();
                if (!isCardInList(randomCardID, randomCards))  {
                    randomCards.add(randomCardID);
                }
            } while (!isCardInList(randomCardID, randomCards));
        }
        return randomCards;
    }

//    Check if card is in (enemy) List
    public boolean isCardInList(String cardID, List<String> cards) {
        for (String card : cards ) {
            if (card.equals(cardID)) return true;
        }
        return false;
    }

//    Check if card is in own List
    public boolean isCardInCollection(String cardID) {
        for (Card card : this.cards ) {
            if (card.getID().equals(cardID)) return true;
        }
        return false;
    }
}
