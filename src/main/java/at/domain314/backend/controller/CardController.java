package at.domain314.backend.controller;

import at.domain314.backend.httpserver.server.Controller;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.models.cards.Card;
import at.domain314.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class CardController extends Controller {
    CardRepo cardRepo;

    public CardController(CardRepo cardRepo) { this.cardRepo = cardRepo; }

//    Retrieves a list of cards with the given ids from the cardRepo,
//     checks if the list is not empty and returns the list of cards in JSON format.
    public Response getCards(List<String> cardIDs) {
        try {
            List<Card> cards = cardRepo.getCards(cardIDs);
            if (cards.size() != 0) {
                String playerDataJSON = this.getObjectMapper().writeValueAsString(cards);
                return new Response("All Cards:\n" + playerDataJSON + "\n", true);
            }
            return new Response(Constants.RESPONSE_BAD_CARDS);

        } catch (JsonProcessingException e) {
            return new Response();
        }
    }

//    Retrieves a list of cards with the given ids from the cardRepo, checks if the list is not empty and
//     returns the list of cards in plain text format, or
//    if the list is empty, it returns a response with a message 'No Cards'
    public Response getCardsPlainFormat(List<String> cardIDs) {
        try {
            List<Card> cards = cardRepo.getCards(cardIDs);
            if (cards.size() != 0) {
                String playerDataJSON = drawPlainFormat(cards);
                return new Response("All Cards:\n" + playerDataJSON + "\n", true);
            }
            return new Response(Constants.RESPONSE_BAD_CARDS);

        } catch (Exception e) {
            return new Response();
        }
    }

//    Takes a list of cards as input and returns a plain text representation of the cards, including
//     each card's name, damage, type, element, description and id, in a string format.
    private String drawPlainFormat(List<Card> cards) {
        String result = "\nDeck: \n";
        for (Card card : cards) {
            result += card.getName() + " - " + card.getDamage() + " - " + card.getType().toString() + " - " + card.getElement() + " - " + card.getDescription() + " - " + card.getID() + "\n";
        }
        return result;
    }

    public List<Card> getAllCards(List<String> cardIDs) {
        try {
            return cardRepo.getCards(cardIDs);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
