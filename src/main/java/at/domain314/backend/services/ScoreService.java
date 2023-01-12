package at.domain314.backend.services;

import at.domain314.backend.controller.ScoreController;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.ScoreRepo;

public class ScoreService implements Service {
    private final ScoreController scoreController;

    public ScoreService() { this.scoreController = new ScoreController(new ScoreRepo()); }

    @Override
    public Response handleRequest(Request request) {

        switch (request.getMethod()) {
            case GET -> { return this.scoreController.getScore(); }
            default -> { return new Response(true); }
        }
    }

}
