package at.domain314.backend.controller;

import at.domain314.backend.httpserver.server.Controller;
import at.domain314.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.SessionRepo;
import at.domain314.models.users.User;

public class SessionController extends Controller {
    SessionRepo sessionRepo;

    public SessionController(SessionRepo sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

//    Tries to log in and returns the outcome
    public Response loginUser(Request request) {
        try {
            User user = this.getObjectMapper().readValue(request.getBody(), User.class);

            switch (this.sessionRepo.login(user)) {
                case 0: return new Response(Constants.RESPONSE_BAD_LOGIN_NOT_EXIST);
                case 1: return new Response(Constants.RESPONSE_BAD_LOGIN_WRONG_PW);
                case 2: return new Response(Constants.RESPONSE_OK_LOGIN, true);
            }
            return new Response(Constants.RESPONSE_BAD_SESSION);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response();
        }
    }
}
