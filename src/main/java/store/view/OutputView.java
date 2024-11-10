package store.view;

import store.entity.Product;

import java.util.List;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    private static final String PURCHASE_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String ADD_MESSAGE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String NONE_PROMOTION_MESSAGE = "현재 %s %s개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";

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

    public static void printInputAdd() {
        System.out.println(ADD_MESSAGE);
    }

    public static void printInputNonePromotion(String name, int remainRequestSize) {
        System.out.printf(NONE_PROMOTION_MESSAGE, name, remainRequestSize);
    }
}
