package store.validator;

import java.util.List;


public class ParseValidator {
    private static final int MIN = 1;
    public static void validateTokens(List<String> tokens) {
        if (tokens.size() == 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
        for (String token : tokens) {
            validateToken(token);
        }
    }

    private static void validateToken(String token) {
        if (token.length() < 4 || !token.startsWith("[") || !token.endsWith("]")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
        List<String> tokenParts = List.of(token.substring(1, token.length() - 1).split("-"));
        if (tokenParts.size() != 2 || tokenParts.get(0).length() == 0 || !isValidInteger(tokenParts.get(1))) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }

    private static boolean isValidInteger(String s) {
        try {
            int n = Integer.parseInt(s);
            return isValidRange(n);
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidRange(int n) {
        if (n > MIN) {
            return true;
        }
        return false;
    }
}
