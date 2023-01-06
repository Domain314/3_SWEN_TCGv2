package at.domain314.backend.services;

import at.domain314.backend.controller.BattleController;
import at.domain314.backend.controller.UserController;
import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.BattleRepo;
import at.domain314.backend.repositories.UserRepo;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;

public class BattleService implements Service {
    private final BattleController battleController;
    private final UserController userController;

    public BattleService() {
        this.battleController = new BattleController(new BattleRepo());
        this.userController = new UserController(new UserRepo());
    }

    @Override
    public Response handleRequest (Request request) {
        Player player = userController.authPlayer(request);
        if (player == null) {
            return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                Constants.RESPONSE_BAD_REQUEST
        ); }

        switch (request.getMethod()) {
            case POST: {
                return this.battleController.queueForBattle(player);
            }
            default: return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    Constants.RESPONSE_BAD_REQUEST
            );
        }
    }

}
