package at.domain314.models.cards;

import at.domain314.utils.Constants;

import java.util.List;

public class Deck extends Collection implements IPlayable{
    public Deck() {}
    public Deck(List<Card> cards) { this.cards = cards; }

    @Override
    public Card drawCard() {
        if (cards.size() == 0) return null;
        int rnd = Constants.RANDOM.nextInt(this.getCards().size());
        return this.getCards().remove(rnd);
    }

    @Override
    public void shuffleCards() { System.out.println("shuffle..."); }
}
