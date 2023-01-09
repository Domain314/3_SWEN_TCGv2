package at.domain314.backend.services;

import at.domain314.backend.controller.CardController;
import at.domain314.backend.controller.UserController;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.backend.repositories.UserRepo;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;

public class CardService implements Service {

    private final CardController cardController;
    private final UserController userController;

    public CardService() {
        this.cardController = new CardController(new CardRepo());
        this.userController = new UserController(new UserRepo());
    }

    @Override
    public Response handleRequest(Request request) {
        Player player = userController.authPlayer(request);
        if (player == null) { return new Response(Constants.RESPONSE_BAD_AUTH); }

        switch (request.getMethod()) {
            case GET: {
                return this.cardController.getCards(player.getStackIDs());
            }
            default: return new Response(true);
        }
    }
}
