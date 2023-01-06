package at.domain314.backend.services;

import at.domain314.backend.controller.SessionController;
import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.SessionRepo;
import at.domain314.utils.Constants;

public class SessionService implements Service {
    private final SessionController sessionController;

    public SessionService() {
        this.sessionController = new SessionController(new SessionRepo());
    }

    @Override
    public Response handleRequest(Request request) {

        switch (request.getMethod()) {
//            case GET: return new Response(HttpStatus.OK, ContentType.JSON, "{\"message\" : \"Get...\"}");
            case POST: return this.sessionController.loginUser(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                Constants.RESPONSE_BAD_REQUEST
        );
    }
}
