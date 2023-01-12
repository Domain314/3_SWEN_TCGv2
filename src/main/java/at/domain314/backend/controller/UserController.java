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

//    Tries to create a User, based on the request and returns the outcome.
    public Response createUser(Request request) {
        try {
            User user = this.getObjectMapper().readValue(request.getBody(), User.class);
            String userDataJSON = this.getObjectMapper().writeValueAsString(user);

            switch (this.userRepo.create(user)) {
                case 0: return new Response(Constants.RESPONSE_BAD_USER_EXISTS);
                case 1: return new Response("New User created:\n" + userDataJSON + "\n", true);
                default:  return new Response(Constants.RESPONSE_BAD_ERROR);
            }
        } catch (Exception e) {
            return new Response();
        }
    }

//    Tries to get the User based on the request and returns the outcome
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

//    Will return a user object if the user is authenticated or null if not.
    private User authUser(Request request) {
        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        return this.userRepo.getUser(user);
    }

//    Will return a player object if the user is authenticated or null if not.
    public Player authPlayer(Request request) {
        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        user.setUserID(userRepo.getIdForToken(user.getToken()));
        return this.userRepo.getPlayer(user);
    }

//    Update a player by authenticating the user, deserializing the request body to a player object,
//     merging in all changes from the request, updating the player with the new data, and
//     returning a response indicating success or failure or an error message.
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

//    Merge only the data from the request body (player), into the given User Object.
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
