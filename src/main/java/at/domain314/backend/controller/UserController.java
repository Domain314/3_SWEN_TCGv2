package at.domain314.backend.controller;

import at.domain314.models.users.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Controller;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.UserRepo;
import at.domain314.models.users.User;

public class UserController extends Controller {
    UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Response createUser(Request request) {
        try {
            User user = this.getObjectMapper().readValue(request.getBody(), User.class);
            String userDataJSON = this.getObjectMapper().writeValueAsString(user);

            switch (this.userRepo.create(user)) {
                case 0:
                    System.out.println("createUser: user already exists");
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "User already exists!");
                case 1:
                    System.out.println("createUser: new user created");
                    return new Response(HttpStatus.CREATED, ContentType.JSON, "New User created -- " + userDataJSON);
            }
            return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "No DB Connection -- " + userDataJSON);

        } catch (JsonProcessingException e) {
            return internalError(e);
        }
    }

    public Response getUser(Request request) {
        try {
            Player playerData = authUser(request);

            if (playerData == null) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Token incorrect");
            } else {
                String playerDataJSON = this.getObjectMapper().writeValueAsString(playerData);
                return new Response(HttpStatus.OK, ContentType.JSON, "Token accepted -- " + playerDataJSON);
            }

        } catch (JsonProcessingException e) {
            return internalError(e);
        }
    }

    private User authUser(Request request) {
        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        user.setUserID(userRepo.getIdForToken(user.getToken()));
        return this.userRepo.getUser(user);
    }

    public Player authPlayer(Request request) {
        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        user.setUserID(userRepo.getIdForToken(user.getToken()));
        return this.userRepo.getPlayer(user);
    }

    public boolean authBoolean(Request request) {
        String token = request.getHeaderMap().getHeader("Authorization");
        return userRepo.playerExists(token);
    }

    public Response updatePlayer(Request request) {
        try {
            String authorizationToken = request.getHeaderMap().getHeader("Authorization");
            User user = authUser(request);
            Player newData = this.getObjectMapper().readValue(request.getBody(), Player.class);
            mergePlayer(user, newData);
            Player playerData = this.userRepo.update(user);

            String userDataJSON = this.getObjectMapper().writeValueAsString(playerData);

            return new Response(HttpStatus.OK, ContentType.JSON, userDataJSON);


        } catch (JsonProcessingException e) {
            return internalError(e);
        }
    }

    private void mergePlayer(User user, Player player) {
        if (player.getName() != null) { user.setName(player.getName()); }
        if (player.getBio() != null) { user.setBio(player.getBio()); }
        if (player.getImage() != null) { user.setImage(player.getImage()); }
        if (player.getCredits() != 0) { user.setCredits(player.getCredits()); }
        if (player.getElo() != 0) { user.setElo(player.getElo()); }
        if (player.getGamesCounter() != 0) { user.setGamesCounter(player.getGamesCounter()); }
        if (player.getWinCounter() != 0) { user.setWinCounter(player.getWinCounter()); }
    }

    public int getIdForToken(String token) {
        return userRepo.getIdForToken(token);
    }
}
