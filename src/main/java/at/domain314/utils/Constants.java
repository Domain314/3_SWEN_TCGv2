package at.domain314.utils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Constants {

//    Game Logic
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

//    Token creation
    private static final int TOKEN_SEGMENT_AMOUNT = 4;
    private static final int TOKEN_SEGMENT_LENGTH = 6;

//    Response Error Messages
    public static final String RESPONSE_BAD_ERROR = "An Error occurred.\n";
    public static final String RESPONSE_BAD_REQUEST = "No Method detected.\n";
    public static final String RESPONSE_BAD_AUTH = "Authentication failed.\n";
    public static final String RESPONSE_BAD_CREDITS = "Not enough Credits.\n";
    public static final String RESPONSE_BAD_CARDS = "No Cards.\n";
    public static final String RESPONSE_BAD_CARDS_WRONG = "Wrong Cards.\n";
    public static final String RESPONSE_BAD_CARDS_NOT_4 = "Not 4 Cards.\n";
    public static final String RESPONSE_BAD_CARDS_DECK = "Not enough Cards in Deck.\n";
    public static final String RESPONSE_BAD_CARDS_DECK_UPDATE = "Error updating Deck.\n";
    public static final String RESPONSE_BAD_CARDS_STACK = "Not enough Cards in Stack.\n";
    public static final String RESPONSE_BAD_USER = "Wrong user.\n";
    public static final String RESPONSE_BAD_ACQUIRE = "Error acquiring Package!\n";
    public static final String RESPONSE_BAD_PACKAGE = "Error creating Package!\n";
    public static final String RESPONSE_BAD_SESSION = "No DB Connection\n";
    public static final String RESPONSE_BAD_LOGIN_NOT_EXIST = "User does not exist!\n";
    public static final String RESPONSE_BAD_LOGIN_WRONG_PW = "Incorrect Password!\n";
    public static final String RESPONSE_BAD_USER_EXISTS = "User already exists!\n";


    public static final String RESPONSE_OK_DECK_UPDATED = "Updated Deck.\n";
    public static final String RESPONSE_OK_BATTLE_QUEUED = "Queued up for battle.\n";
    public static final String RESPONSE_OK_PACKAGE = "Package acquired!\n";
    public static final String RESPONSE_OK_LOGIN = "Successfully logged in!\n";

    public static final Random RANDOM = new Random();

    public static String createToken(String username) {
//        return generateToken();
        return "Basic " + username + "-mtcgToken";
    }

//    Create Card ID in the format xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
    public static String createCardID() {
        return generateRandomString(8) + "-" +
                generateRandomString(4) + "-" +
                generateRandomString(4) + "-" +
                generateRandomString(4) + "-" +
                generateRandomString(12);
    }

//    Make X segments, with length Y.
//    Unused, for curl script
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
        // String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";  // All chars
        String characters = "0123456789abcdef";                                                   // HEX
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
                newList.add(t.toString());
            }
            return newList;

        } catch (Exception e) { throw new RuntimeException(e); }
    }


//    The Element Matrix is visually defined in the documentation.
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

    public static void print(String content) {
        System.out.println(content);
    }
}
