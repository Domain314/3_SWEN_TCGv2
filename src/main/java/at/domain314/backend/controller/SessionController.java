package at.domain314.backend.controller;

import at.domain314.backend.httpserver.server.Controller;
import at.domain314.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.SessionRepo;
import at.domain314.models.users.User;

public class SessionController extends Controller {
    SessionRepo sessionRepo;

    public SessionController(SessionRepo sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    public Response loginUser(Request request) {
        try {
            User user = this.getObjectMapper().readValue(request.getBody(), User.class);

            switch (this.sessionRepo.login(user)) {
                case 0:
                    Constants.print("login failed: user does not exist");
                    return new Response("User does not exist!\n");
                case 1:
                    Constants.print("login failed: incorrect password");
                    return new Response("Incorrect Password\n");
                case 2:
                    Constants.print("login successful");
                    return new Response("Successfully logged in!\n", true);
            }
            return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "No DB Connection\n");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, Constants.RESPONSE_BAD_REQUEST);
        }
    }
}
