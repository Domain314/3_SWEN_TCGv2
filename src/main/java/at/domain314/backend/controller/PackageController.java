package at.domain314.backend.controller;

import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Controller;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.backend.repositories.PackageRepo;
import at.domain314.models.cards.Card;
import at.domain314.models.cards.CardPackage;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;

import java.util.List;

public class PackageController extends Controller {
    PackageRepo packageRepo;
    CardRepo cardRepo;

    public PackageController(PackageRepo packageRepo, CardRepo cardRepo) {
        this.packageRepo = packageRepo;
        this.cardRepo = cardRepo;
    }

    public Response createPackage(Request request) {
        try {
            Card[] cards = null;
            if (request.getBody() != null && request.getBody() != "") {
                cards = this.getObjectMapper().readValue(request.getBody(), Card[].class);
            }
            List<String> cardIDs = this.cardRepo.createCards(this.packageRepo.createPackage(cards));

            switch(this.packageRepo.uploadPackage(cardIDs)) {
                case 1:
                    Constants.print("Package created!");
                    return new Response("Package created!\n", true);
                default:
                    Constants.print(Constants.RESPONSE_BAD_PACKAGE);
                    return new Response(Constants.RESPONSE_BAD_PACKAGE);
            }
        } catch (Exception e) {
            Constants.print(Constants.RESPONSE_BAD_PACKAGE);
            return new Response(Constants.RESPONSE_BAD_PACKAGE);
        }
    }

    public Response acquirePackage(Request request, Player player) {
        try {
            CardPackage cardPackage = this.packageRepo.getOneAvailablePackage();
            System.out.println(cardPackage);
            if (cardPackage == null) return new Response(Constants.RESPONSE_BAD_ACQUIRE);

            switch(this.packageRepo.transferPackage(cardPackage, player)) {
                case 1:
                    Constants.print("Package acquired!");
                    return new Response("Package acquired!\n", true);
                default:
                    Constants.print(Constants.RESPONSE_BAD_ACQUIRE);
                    return new Response(Constants.RESPONSE_BAD_ACQUIRE);
            }
        } catch (Exception e) {
            Constants.print(Constants.RESPONSE_BAD_ACQUIRE);
            return new Response(Constants.RESPONSE_BAD_ACQUIRE);
        }
    }
}
