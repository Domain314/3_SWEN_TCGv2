package at.domain314.models.cards;

import at.domain314.utils.Constants;

import java.sql.Array;
import java.util.List;

public class CardPackage {
    List<String> cardIDs;
    int id;

    public CardPackage() {}
    public CardPackage(int id, Array cardIDs) {
        this.id = id;
        this.cardIDs = Constants.convertArrayToList(cardIDs);
    }

    public List<String> getCardIDs() { return this.cardIDs; }
    public String getCardID(int index) { return this.cardIDs.get(index); }
    public int getID() { return this.id; }

    public void setCardIDs(List<String> cardIDs) { this.cardIDs = cardIDs; }
    public void setID(int id) { this.id = id; }
}
