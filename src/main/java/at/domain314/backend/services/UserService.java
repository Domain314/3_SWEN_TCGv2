package at.domain314.backend.services;

import at.domain314.backend.controller.UserController;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.UserRepo;

public class UserService implements Service {

    private final UserController userController;

    public UserService() {
        this.userController = new UserController(new UserRepo());
    }

    @Override
    public Response handleRequest(Request request) {

        switch (request.getMethod()) {
            case POST -> { return this.userController.createUser(request); }
            case GET -> { return this.userController.getUser(request); }
            case PUT -> { return this.userController.updatePlayer(request); }
            default -> { return new Response(true); }
        }
    }
}
