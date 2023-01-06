package at.domain314.utils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Constants {
    public static final int MAX_ROUNDS_PER_GAME = 100;
    public static final int CARDS_PER_DECK = 4;
    public static final int CARDS_PER_PACKAGE = 5;
    public static final int RANDOM_ELEMENT_CHANCE = 6;
    public static final int RANDOM_TYPE_CHANCE = 10;
    public static final int RANDOM_ID_FROM = 100000;
    public static final int RANDOM_ID_TO = 999999;
    public static final int RANDOM_DAMAGE_FROM = 1;
    public static final int RANDOM_DAMAGE_TO = 20;
    private static final int ELO_CONSTANT = 50;

    private static final int TOKEN_SEGMENT_AMOUNT = 4;
    private static final int TOKEN_SEGMENT_LENGTH = 6;

    public static final String RESPONSE_BAD_REQUEST = "{ \"message\" : \"No Method detected.\" }";

    public static final Random RANDOM = new Random();

    public static String createToken(String username) {
//        return generateToken();
        return "Basic " + username + "-mtcgToken";
    }

    public static String createCardID() {
        return generateRandomString(8) + "-" +
                generateRandomString(4) + "-" +
                generateRandomString(4) + "-" +
                generateRandomString(4) + "-" +
                generateRandomString(12);
    }

    private static String generateToken() {
        String newToken = "";
        int segments = 0;
        while (segments < TOKEN_SEGMENT_AMOUNT) {
            newToken += generateRandomString(TOKEN_SEGMENT_LENGTH);
            if (segments != TOKEN_SEGMENT_AMOUNT) {
                newToken += "-";
            }
        }
        return newToken;
    }

    private static String generateRandomString(int length) {
        // Possible characters in the random string
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";  // All chars
        String characters = "abcdef0123456789";                                                  // HEX
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while (sb.length() < length) {
            // nextFloat() instead of nextInt(): nextFloat() is inclusive max n, nextInt() is exclusive max n. With nextInt(), you have to add n + 1, for a correct range.
            int index = (int) (random.nextFloat() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public static List<String> convertArrayToList(Array arr) {
        try {
            if (arr == null) return null;
            List<String> newList = new ArrayList<>();
            Object[] temp = (Object[]) arr.getArray();
            for (Object t : temp) {
                System.out.println(t);
                newList.add(t.toString());
            }
            return newList;

        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static final float[][] ELEMENT_MATRIX = {
            {1.0f, 0.5f, 2.0f, 0.66f, 1.25f},
            {2.0f, 1.0f, 0.66f, 0.5f, 1.25f},
            {0.5f, 0.66f, 1.0f, 2.0f, 1.25f},
            { 0.66f, 2.0f, 0.5f, 1.0f, 1.25f},
            {0.75f, 0.75f, 0.75f, 0.75f, 1.0f},
    };

    //    return amount of elo-change, based on winner and loser rating
    public static int EloChange(float ratingWinner, float ratingLoser) {
        return (int)(ELO_CONSTANT * (1 - probability(ratingLoser, ratingWinner)));
    }
    //    calculate win-probability for elo-calculation
    private static float probability(float rating1, float rating2) {
        return 1.0f / (1 + (float) (Math.pow(10, (rating1 - rating2) / 400)));
    }
}
