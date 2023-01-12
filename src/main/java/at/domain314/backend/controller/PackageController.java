package at.domain314.backend.controller;

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

//    Deserialize a request body, creating cards and a package, uploading the package,
//     and returning a response indicating success or failure.
    public Response createPackage(Request request) {
        try {
            Card[] cards = null;
            if (request.getBody() != null && request.getBody() != "") {
                cards = this.getObjectMapper().readValue(request.getBody(), Card[].class);
            }
            List<String> cardIDs = this.cardRepo.createCards(this.packageRepo.createPackage(cards));

            switch(this.packageRepo.uploadPackage(cardIDs)) {
                case 1:
                    return new Response("Package created!\n", true);
                default:
                    return new Response(Constants.RESPONSE_BAD_PACKAGE);
            }
        } catch (Exception e) {
            return new Response(Constants.RESPONSE_BAD_PACKAGE);
        }
    }

//    Acquire a package by getting one available package, transferring it to a player, and
//     returning a response indicating success or failure.
    public Response acquirePackage(Request request, Player player) {
        try {
            CardPackage cardPackage = this.packageRepo.getOneAvailablePackage();
            System.out.println(cardPackage);
            if (cardPackage == null) return new Response(Constants.RESPONSE_BAD_ACQUIRE);

            switch(this.packageRepo.transferPackage(cardPackage, player)) {
                case 1:
                    return new Response(Constants.RESPONSE_OK_PACKAGE, true);
                default:
                    return new Response(Constants.RESPONSE_BAD_ACQUIRE);
            }
        } catch (Exception e) {
            Constants.print(Constants.RESPONSE_BAD_ACQUIRE);
            return new Response(Constants.RESPONSE_BAD_ACQUIRE);
        }
    }
}
