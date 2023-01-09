package at.domain314.backend.controller;

import at.domain314.models.users.Player;
import at.domain314.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
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
                    Constants.print("User already exists!\n");
                    return new Response("User already exists!\n");
                }
                case 1:{
                    Constants.print("New User created:\n" + userDataJSON + "\n");
                    return new Response("New User created:\n" + userDataJSON + "\n", true);
                }
                default: {
                    Constants.print(Constants.RESPONSE_BAD_ERROR);
                    return new Response(Constants.RESPONSE_BAD_ERROR);
                }
            }
        } catch (Exception e) {
            return new Response();
        }
    }

    public Response getUser(Request request) {
        try {
            User user = authUser(request);
            if (user == null) { return new Response(Constants.RESPONSE_BAD_AUTH); }
            if (user.getUsername().equals(request.getPathParts().get(1))) {
                String playerDataJSON = this.getObjectMapper().writeValueAsString((Player)user);
                return new Response("Token accepted.\nPlayer Data:\n" + playerDataJSON + "\n", true);
            }
            return new Response(Constants.RESPONSE_BAD_AUTH);
        } catch (Exception e) {
            return new Response();
        }
    }

    private User authUser(Request request) {
        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        return this.userRepo.getUser(user);
    }

    public Player authPlayer(Request request) {
        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        user.setUserID(userRepo.getIdForToken(user.getToken()));
        return this.userRepo.getPlayer(user);
    }

    public Response updatePlayer(Request request) {
        try {
            User user = authUser(request);
            Player newData = this.getObjectMapper().readValue(request.getBody(), Player.class);
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

}
