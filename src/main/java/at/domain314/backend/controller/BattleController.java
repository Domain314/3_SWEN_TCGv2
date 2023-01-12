package at.domain314.backend.controller;

import at.domain314.backend.httpserver.server.Controller;
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

//    Queues a player for battle, checking if the player's deck is valid before adding them to the battle queue.
//    If there are enough players in the queue, a battle is started and the outcome is returned in the response.
    public Response queueForBattle(Player player) {
        if (player.getDeckIDs() == null) {
            return new Response(Constants.RESPONSE_BAD_CARDS_DECK);
        }
        if (player.getDeckIDs().size() != Constants.CARDS_PER_DECK) {
            return new Response(Constants.RESPONSE_BAD_CARDS_DECK);
        }
        battleQueue.add(player);
        System.out.println(battleQueue.size() + " Player(s) in queue.\n");
        if (battleQueue.size() > 1) {
            return new Response(startBattle(lastOutcomes.size()-1) + "\n", true);
        } else {
            return new Response(Constants.RESPONSE_OK_BATTLE_QUEUED, true);
        }
    }

//    Starts a battle by removing the first two players from the battle queue and
//     creating a new Game object with these players.
//    The game is then run and the outcome is stored in a variable 'result'.
//    After the game, the battleRepo is updated to reflect the results of the battle.
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
//    This private method runs the game by repeatedly calling the makeRound() method of the game until
//     it's not active anymore.
//    The outcome of the game is returned as a string
    private String runGame(Game newGame, int index) {
        String outcome = "-";
        while(newGame.getIsActive()) {
            outcome = newGame.makeRound();
        }
        return outcome;
    }
}
