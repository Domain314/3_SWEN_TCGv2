package at.domain314.models.cards;

import at.domain314.utils.Constants;

public class Deck extends Collection implements IPlayable{
    @Override
    public Card drawCard() {
        int rnd = Constants.RANDOM.nextInt(this.getCards().size());
        return this.getCards().remove(rnd);
    }

    @Override
    public void shuffleCards() { System.out.println("shuffle..."); }
}
