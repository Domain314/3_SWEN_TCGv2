package at.domain314.backend.services;

import at.domain314.backend.controller.SessionController;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.SessionRepo;

public class SessionService implements Service {
    private final SessionController sessionController;

    public SessionService() {
        this.sessionController = new SessionController(new SessionRepo());
    }

    @Override
    public Response handleRequest(Request request) {

        switch (request.getMethod()) {
            case POST -> { return this.sessionController.loginUser(request); }
            default -> { return new Response(true); }
        }
    }
}
