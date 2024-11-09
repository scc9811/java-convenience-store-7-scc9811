package store.util;

import java.text.NumberFormat;

public class ParseUtil {
    private static final NumberFormat FORMATTER = NumberFormat.getInstance();
    private ParseUtil() {
    }

    public static String numberFormat(int n) {
        return FORMATTER.format(n);
    }

    public static String removeSpace(String input) {
        return input.replaceAll("\\s+", "");
    }

    public static boolean isGiftSelected(String addInput) {
        addInput = removeSpace(addInput);
        if (addInput.equals("N")) {
            return false;
        }
        return true;
    }
}
