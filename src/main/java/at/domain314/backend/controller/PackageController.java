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
            System.out.println(request);
            if (request.getBody() != null && request.getBody() != "") {
                cards = this.getObjectMapper().readValue(request.getBody(), Card[].class);
            }
            List<String> cardIDs = this.cardRepo.createCards(this.packageRepo.createPackage(cards));

            switch(this.packageRepo.uploadPackage(cardIDs)) {
                case 0:
                    System.out.println("Error creating Package!");
                    return new Response(HttpStatus.CREATED, ContentType.JSON, "Error creating Package!\n");
                case 1:
                    System.out.println("Package created!");
                    return new Response(HttpStatus.CREATED, ContentType.JSON, "Package created!\n");
            }

            return new Response(HttpStatus.OK, ContentType.JSON, "Okokokokok!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response acquirePackage(Request request, int userID) {
        try {
            CardPackage cardPackage = this.packageRepo.getOneAvailablePackage();

            switch(this.packageRepo.transferPackage(cardPackage, userID)) {
                case 0:
                    System.out.println("Error acquiring Package!");
                    return new Response(HttpStatus.NO_CONTENT, ContentType.JSON, "Error acquiring Package!\n");
                case 1:
                    System.out.println("Package acquired!");
                    return new Response(HttpStatus.CREATED, ContentType.JSON, "Package acquired!\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Error acquiring Package!\n");
    }

}
