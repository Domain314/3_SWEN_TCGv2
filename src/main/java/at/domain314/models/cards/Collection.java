package at.domain314.models.cards;

import java.util.ArrayList;
import java.util.List;

abstract public class Collection {
    List<Card> cards = new ArrayList<>();

    public Collection() {
        System.out.println("new Collection made");
    }

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

    public void deleteCard(Card card) {
        cards.remove(card);
    }

    public void swapCard() {
        // to do
    }
}
