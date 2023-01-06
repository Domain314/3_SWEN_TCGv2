package at.domain314.backend.services;

import at.domain314.backend.controller.PackageController;
import at.domain314.backend.controller.UserController;
import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.backend.repositories.PackageRepo;
import at.domain314.backend.repositories.UserRepo;
import at.domain314.utils.Constants;

public class PackageService implements Service {
    private final PackageController packageController;
    private final UserController userController;

    public PackageService() {
        this.packageController = new PackageController(new PackageRepo(), new CardRepo());
        this.userController = new UserController(new UserRepo());
    }

    @Override
    public Response handleRequest(Request request) {

        switch (request.getPathParts().get(0)) {
            case "packages": {
                return this.packageController.createPackage(request);

            }
            case "transactions": {
                int userID = userController.getIdForToken(request.getHeaderMap().getHeader("Authorization"));
                if (userID == 0) { return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        Constants.RESPONSE_BAD_REQUEST
                ); }
                return this.packageController.acquirePackage(request, userID);
            }
            default: return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    Constants.RESPONSE_BAD_REQUEST
            );
        }
    }

}
