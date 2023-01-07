package at.domain314.backend.services;

import at.domain314.backend.controller.PackageController;
import at.domain314.backend.controller.UserController;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.backend.repositories.PackageRepo;
import at.domain314.backend.repositories.UserRepo;
import at.domain314.models.users.Player;
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
        Player player = userController.authPlayer(request);
        if (player == null) return new Response(Constants.RESPONSE_BAD_AUTH);

        switch (request.getPathParts().get(0)) {
            case "packages": {
                return this.packageController.createPackage(request);
            }
            case "transactions": {
                if (player.getCredits() >= 5) return this.packageController.acquirePackage(request, player);
                else return new Response(Constants.RESPONSE_BAD_CREDITS);
            }
            default: return new Response();
        }
    }

}
