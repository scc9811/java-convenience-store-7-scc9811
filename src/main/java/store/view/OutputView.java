package store.view;

import store.entity.Product;

import java.util.List;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    private static final String PURCHASE_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";

    private OutputView() {
    }

    public static void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public static void printProductsInfo(List<Product> products) {
        for (Product product : products) {
            System.out.println(product.info());
        }
        System.out.println();
    }

    public static void printInputPurchase() {
        System.out.println(PURCHASE_MESSAGE);
    }
}
