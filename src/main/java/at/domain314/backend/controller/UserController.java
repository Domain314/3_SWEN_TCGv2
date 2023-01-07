package at.domain314.backend.controller;

import at.domain314.models.users.Player;
import at.domain314.utils.Constants;
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
                case 0: {
                    return new Response("User already exists!");
                }
                case 1:{
                    return new Response("New User created:\n" + userDataJSON, true);
                }
                default: {
                    return new Response();
                }
            }
        } catch (Exception e) {
            return new Response();
        }
    }

    public Response getUser(Request request) {
        try {
            Player player = authPlayer(request);
            if (player == null) { return new Response(Constants.RESPONSE_BAD_AUTH); }
            if (player.getName().equals(request.getPathParts().get(1))) {
                String playerDataJSON = this.getObjectMapper().writeValueAsString(player);
                return new Response("Token accepted:\n" + playerDataJSON, true);
            }
            return new Response(Constants.RESPONSE_BAD_AUTH);
        } catch (Exception e) {
            return new Response();
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
            User user = authUser(request);
            Player newData = this.getObjectMapper().readValue(request.getBody(), Player.class);
            System.out.println(user.getUsername());
            System.out.println(request.getPathParts().get(1));
            if (!user.getUsername().equals(request.getPathParts().get(1))) {
                return new Response(Constants.RESPONSE_BAD_USER);
            }
            mergePlayer(user, newData);
            Player playerData = this.userRepo.update(user);

            String userDataJSON = this.getObjectMapper().writeValueAsString(playerData);

            return new Response(userDataJSON, true);


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
