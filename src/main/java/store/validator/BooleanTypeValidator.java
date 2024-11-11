package store.validator;

public class BooleanTypeValidator {
    private BooleanTypeValidator() {
    }

    public static void validate(String s) {
        validateLength(s);
        validateValue(s.charAt(0));
    }

    private static void validateLength(String s) {
        if (s.length() != 1) {
            throw new IllegalArgumentException(ErrorMessage.ELSE.getMessage());
        }
    }

    private static void validateValue(char c) {
        if (c != 'Y' && c != 'N') {
            throw new IllegalArgumentException(ErrorMessage.ELSE.getMessage());
        }
    }

}
