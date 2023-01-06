package at.domain314.backend.controller;

import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Controller;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.DeckRepo;
import at.domain314.models.cards.Card;
import at.domain314.models.users.Player;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class DeckController extends Controller {
    DeckRepo deckRepo;

    public DeckController(DeckRepo deckRepo) { this.deckRepo = deckRepo; }

    public Response updateDeck(Request request, int id) {
        try {

            System.out.println(request.getBody());
            if (request.getBody() == null && request.getBody() == "") {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No Cards to add.\n");
            }
            String[] cards = this.getObjectMapper().readValue(request.getBody(), String[].class);
            if (cards.length != 4) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not 4 Cards.\n");
            }
            if (!this.deckRepo.update(cards, id)) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Error updating Deck.\n");
            };

            return new Response(HttpStatus.OK, ContentType.JSON, "Updated Deck.\n");


        } catch (JsonProcessingException e) {
            return internalError(e);
        }
    }
}
