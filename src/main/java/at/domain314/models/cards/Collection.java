package at.domain314.models.cards;

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

    public boolean isCardInList(String cardID, List<String> cards) {
        for (String card : cards ) {
            if (card.equals(cardID)) return true;
        }
        return false;
    }

    public boolean isCardInCollection(String cardID) {
        for (Card card : this.cards ) {
            if (card.getID().equals(cardID)) return true;
        }
        return false;
    }

    public void deleteCard(Card card) {
        cards.remove(card);
    }

    public void swapCard() {
        // to do
    }
}
