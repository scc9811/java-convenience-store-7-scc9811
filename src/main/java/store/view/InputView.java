package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.util.ParseUtil;

public class InputView {
    private InputView() {
    }

    public static String getUserInput() {
        String input = Console.readLine();
        return ParseUtil.removeSpace(input);
    }
}
