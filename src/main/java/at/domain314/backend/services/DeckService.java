package at.domain314.backend.services;

import at.domain314.backend.controller.CardController;
import at.domain314.backend.controller.DeckController;
import at.domain314.backend.controller.UserController;
import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.backend.repositories.DeckRepo;
import at.domain314.backend.repositories.UserRepo;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;

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
        System.out.println(request.getParams());

        Player player = userController.authPlayer(request);
        if (player == null) { return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                Constants.RESPONSE_BAD_REQUEST
        ); }

        switch (request.getMethod()) {
            case GET: {
                if (request.getParams().contains("format")) { return this.cardController.getCardsPlainFormat(player.getDeckIDs()); }
                else { return this.cardController.getCards(player.getDeckIDs()); }
            }
            case PUT: {
                return this.deckController.updateDeck(request, player.getID());
            }
            default: return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    Constants.RESPONSE_BAD_REQUEST
            );
        }
    }
}
