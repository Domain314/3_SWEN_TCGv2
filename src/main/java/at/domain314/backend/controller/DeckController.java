package at.domain314.backend.controller;

import at.domain314.backend.httpserver.server.Controller;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.DeckRepo;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;

public class DeckController extends Controller {
    DeckRepo deckRepo;

    public DeckController(DeckRepo deckRepo) { this.deckRepo = deckRepo; }

//    Updates a player's deck with cards provided in a request object, checking for the existence of those cards in
//     the player's inventory, and updating the player's deck with the cards.
    public Response updateDeckWithRequest(Request request, Player player) {
        try {
            String[] cards = this.getObjectMapper().readValue(request.getBody(), String[].class);

            if (!checkForCardExistence(player, cards)) { return new Response(Constants.RESPONSE_BAD_CARDS_WRONG); }

            return updateDeckWithCards(cards, player);

        } catch (Exception e) {
            return new Response();
        }
    }

//    Update a player's deck by verifying that the number of cards passed in and
//     updating the player's deck with them, returning a response indicating success or failure.
    public Response updateDeckWithCards(String[] cards, Player player) {
        try {
            if (cards.length != Constants.CARDS_PER_DECK) {
                return new Response(Constants.RESPONSE_BAD_CARDS_NOT_4);
            }
            if (!this.deckRepo.update(cards, player.getID())) {
                return new Response(Constants.RESPONSE_BAD_CARDS_DECK_UPDATE);
            };

            return new Response(Constants.RESPONSE_OK_DECK_UPDATED, true);
        } catch (Exception e) {
            return new Response();
        }
    }

//    Checks if all the cards provided in the card ids array exist in the player's stack.
//    It returns false if all the cards exist, true otherwise.
    private boolean checkForCardExistence(Player player, String[] cards) {
        for (String cardID : cards) {
            if (!player.getStackIDs().contains(cardID)) return false;
        }
        return true;
    }

}
