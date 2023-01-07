package at.domain314.backend.controller;

import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Controller;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.ScoreRepo;

public class ScoreController extends Controller {
    ScoreRepo scoreRepo;

    public ScoreController(ScoreRepo scoreRepo) { this.scoreRepo = scoreRepo; }

    public Response getScore() {
        String result = scoreRepo.getScore();
        if (result.equals("")) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Error retrieving Score");
        }
        return new Response(HttpStatus.CREATED, ContentType.JSON, "ScoreBoard:\n" + result);
    }
}
