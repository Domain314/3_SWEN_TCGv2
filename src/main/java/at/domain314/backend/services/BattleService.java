package at.domain314.backend.services;

import at.domain314.backend.controller.BattleController;
import at.domain314.backend.controller.CardController;
import at.domain314.backend.controller.UserController;
import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.httpserver.server.Service;
import at.domain314.backend.repositories.BattleRepo;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.backend.repositories.UserRepo;
import at.domain314.models.cards.Deck;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;

public class BattleService implements Service {
    private final BattleController battleController;
    private final UserController userController;
    private final CardController cardController;

    public BattleService() {
        this.battleController = new BattleController(new BattleRepo());
        this.userController = new UserController(new UserRepo());
        this.cardController = new CardController(new CardRepo());
    }

    @Override
    public Response handleRequest (Request request) {
        Player player = userController.authPlayer(request);
        if (player == null) { return new Response(Constants.RESPONSE_BAD_AUTH); }

        switch (request.getMethod()) {
            case POST: {
                if (preparePlayer(player)) return this.battleController.queueForBattle(player);
            }
            case GET: {
                return new Response(getStats(player), true);
            }
            default: return new Response();
        }
    }

    private boolean preparePlayer(Player player) {
        try {
            player.setDeck(new Deck(cardController.getAllCards(player.getDeckIDs())));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getStats(Player player) {
        String winRatio = "0";
        if (player.getWinCounter() != 0) {
            winRatio = String.format("%.2f", (float)player.getWinCounter() / player.getGamesCounter());
        }

        return player.getName() + " - Stats\nW\tL/D\tTotal\tW-Ratio\tElo\tCredits\n" +
                player.getWinCounter() + "\t" +
                (player.getGamesCounter()-player.getWinCounter()) + "\t" +
                player.getGamesCounter() + "\t" +
                winRatio + "\t" +
                player.getElo() + "\t" +
                player.getCredits() + "\n";
    }
}
