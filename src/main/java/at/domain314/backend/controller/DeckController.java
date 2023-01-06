package at.domain314.backend.controller;

import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Controller;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.DeckRepo;
import at.domain314.models.cards.Card;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class DeckController extends Controller {
    DeckRepo deckRepo;

    public DeckController(DeckRepo deckRepo) { this.deckRepo = deckRepo; }

    public Response updateDeck(Request request, Player player) {
        try {
            String[] cards = this.getObjectMapper().readValue(request.getBody(), String[].class);

            if (cards.length != 4) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not 4 Cards.\n");
            }
            if (!this.deckRepo.update(cards, player.getID())) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Error updating Deck.\n");
            };

            return new Response(HttpStatus.OK, ContentType.JSON, "Updated Deck.\n");


        } catch (JsonProcessingException e) {
            return internalError(e);
        }
    }

    public Response updateDeck(Request request, Player player, String[] cards) {
        try {
            if (cards.length != Constants.CARDS_PER_DECK) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not 4 Cards.\n");
            }
            if (!this.deckRepo.update(cards, player.getID())) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Error updating Deck.\n");
            };

            return new Response(HttpStatus.OK, ContentType.JSON, "Updated Deck.\n");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
