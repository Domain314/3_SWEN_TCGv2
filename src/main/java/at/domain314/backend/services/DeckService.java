package at.domain314.backend.services;

import at.domain314.backend.controller.CardController;
import at.domain314.backend.controller.DeckController;
import at.domain314.backend.controller.UserController;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.backend.repositories.DeckRepo;
import at.domain314.backend.repositories.UserRepo;
import at.domain314.models.cards.Deck;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;

import java.util.List;

public class DeckService implements Service {
    private final DeckController deckController;
    private final UserController userController;
    private final CardController cardController;

    public DeckService() {
        this.deckController = new DeckController(new DeckRepo());
        this.userController = new UserController(new UserRepo());
        this.cardController = new CardController(new CardRepo());
    }

    @Override
    public Response handleRequest(Request request) {
        Player player = userController.authPlayer(request);
        if (player == null) { return new Response(Constants.RESPONSE_BAD_AUTH); }

        switch (request.getMethod()) {
            case GET: {
                if (player.getDeckIDs() == null) return new Response(Constants.RESPONSE_BAD_CARDS);
                if (player.getDeckIDs().size() == 0) return new Response(Constants.RESPONSE_BAD_CARDS);
                if (request.getParams() != null) {
                    if (request.getParams().contains("format")) {
                        return this.cardController.getCardsPlainFormat(player.getDeckIDs());
                    }
                }
                return this.cardController.getCards(player.getDeckIDs());
            }
            case PUT: {
                if (request.getBody() == null || request.getBody() == "") {
                    return this.deckController.updateDeckWithCards(getBestCards(player), player);
                }
                else {
                    return this.deckController.updateDeckWithRequest(request, player);
                }
            }
            default: return new Response();
        }
    }

    private String[] getBestCards(Player player) {
        Deck deck = new Deck(cardController.getAllCards(player.getStackIDs()));
        List<String> bestCardIDs = deck.getBestCards(Constants.CARDS_PER_DECK);
        String[] bCIDs = bestCardIDs.toArray(new String[Constants.CARDS_PER_DECK]);
        return bCIDs;
    }
}
