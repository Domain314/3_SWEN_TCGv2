package at.domain314.backend.controller;

import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Controller;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.models.cards.Card;
import at.domain314.models.users.Player;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

public class CardController extends Controller {
    CardRepo cardRepo;

    public CardController(CardRepo cardRepo) { this.cardRepo = cardRepo; }

    public Response getCards(List<String> cardIDs) {
        try {
            List<Card> cards = cardRepo.getCards(cardIDs);
            if (cards.size() != 0) {
                String playerDataJSON = this.getObjectMapper().writeValueAsString(cards);
                return new Response(HttpStatus.OK, ContentType.JSON, "All Cards:\n" + playerDataJSON);
            }
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No Cards");

        } catch (JsonProcessingException e) {
            return internalError(e);
        }
    }

    public Response getCardsPlainFormat(List<String> cardIDs) {
        try {
            List<Card> cards = cardRepo.getCards(cardIDs);
            if (cards.size() != 0) {

                String playerDataJSON = drawPlainFormat(cards);
                return new Response(HttpStatus.OK, ContentType.JSON, "All Cards:\n" + playerDataJSON);
            }
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No Cards");

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String drawPlainFormat(List<Card> cards) {
        String result = "\nDeck: \n";
        for (Card card : cards) {
            result += card.getName() + " - " + card.getDamage() + " - " + card.getType().toString() + " - " + card.getElement() + " - " + card.getDescription() + " - " + card.getID() + "\n";
        }
        return result;
    }

    public List<String> createCards(List<Card> cards) {
        return cardRepo.createCards(cards);
    }

}
