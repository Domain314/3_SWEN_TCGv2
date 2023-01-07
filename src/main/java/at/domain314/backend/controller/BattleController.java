package at.domain314.backend.controller;

import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.backend.httpserver.server.Controller;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.BattleRepo;
import at.domain314.game.Game;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BattleController extends Controller {
    BattleRepo battleRepo;
    List<Player> battleQueue = new ArrayList<>();
    List<String> lastOutcomes = new ArrayList<>();

    public BattleController(BattleRepo battleRepo) { this.battleRepo = battleRepo; }

    public Response queueForBattle(Player player) {
        if (player.getDeckIDs() == null) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No cards in deck.\n");
        }
        if (player.getDeckIDs().size() != Constants.CARDS_PER_DECK) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not enough cards in deck, for a battle.\n");
        }
        battleQueue.add(player);
        System.out.println(battleQueue.size());
        if (battleQueue.size() > 1) {
            return new Response(HttpStatus.OK, ContentType.JSON, startBattle(lastOutcomes.size()-1));
        } else {
            return new Response(HttpStatus.OK, ContentType.JSON, "Queued up for battle.");
//            lastOutcomes.add("-");
//            return runQueue(player, lastOutcomes.size()-1);
        }
    }



    private Response runQueue(Player player, int index) {
        while (lastOutcomes.get(index).equals("-")) {
            // run forever
        }
        return new Response(HttpStatus.OK, ContentType.JSON, lastOutcomes.get(index));
    }

    private String startBattle(int index) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            players.add(battleQueue.remove(0));
        }
        Game game = new Game(players);
        String result = runGame(game, index);
        battleRepo.updateAfterBattle(players);
        return result;
    }

    private String runGame(Game newGame, int index) {
        String outcome = "-";
        while(newGame.getIsActive()) {
            outcome = newGame.makeRound();
        }
        return outcome;
    }
}
