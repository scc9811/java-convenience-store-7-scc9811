package store.view;

import store.entity.PresentedProduct;
import store.entity.Product;
import store.entity.Receipt;
import store.entity.RequestItem;

import static store.util.ParseUtil.*;

import java.util.List;

public class OutputView {
    private static final String WELCOME_MESSAGE =
            "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    private static final String PURCHASE_MESSAGE =
            "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String ADD_MESSAGE =
            "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private static final String NONE_PROMOTION_MESSAGE =
            "현재 %s %s개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    private static final String MEMBERSHIP_DISCOUNT_MESSAGE =
            "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String REPURCHASE_MESSAGE =
            "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    private static final String RECEIPT_HEADER =
            "==============W 편의점================\n상품명\t\t수량\t금액\n";
    private static final String PRESENTATION_LINE =
            "=============증\t정===============\n";
    private static final String DIVIDE_LINE =
            "====================================\n";
    private static final String TOTAL_PURCHASE_AMOUNT_FORMAT =
            "총구매액\t\t%s\t%s\n";
    private static final String EVENT_DISCOUNT_FORMAT =
            "행사할인\t\t\t-%s\n";
    private static final String MEMBERSHIP_DISCOUNT_FORMAT =
            "멤버십할인\t\t\t-%s\n";
    private static final String TOTAL_PAY_MONEY_FORMAT =
            "내실돈\t\t\t %s\n";
    private static final String PRODUCT_FORMAT =
            "%s\t\t%s \t%s\n";
    private static final String GIFT_FORMAT =
            "%s\t\t%s\n";

    private OutputView() {
    }

    public static void printReceipt(List<Product> products, Receipt receipt) {
        StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append(RECEIPT_HEADER);
        appendPurchaseProductInfo(products, receipt, receiptBuilder);
        receiptBuilder.append(PRESENTATION_LINE);
        appendGiftInfo(receipt, receiptBuilder);
        receiptBuilder.append(DIVIDE_LINE);
        appendPayInfo(receipt, receiptBuilder);
        System.out.println(receiptBuilder);
    }

    private static void appendPayInfo(Receipt receipt, StringBuilder builder) {
        builder.append(String.format(TOTAL_PURCHASE_AMOUNT_FORMAT,
                numberFormat(receipt.totalPurchasedProductsCount()),
                numberFormat(receipt.getTotalPurchaseAmount())));
        builder.append(String.format(EVENT_DISCOUNT_FORMAT, numberFormat(receipt.getEventDisCount())));
        builder.append(String.format(MEMBERSHIP_DISCOUNT_FORMAT, numberFormat(receipt.getMembershipDiscount())));
        builder.append(String.format(TOTAL_PAY_MONEY_FORMAT,numberFormat(receipt.totalPayMoney())));
    }

    private static void appendGiftInfo(Receipt receipt, StringBuilder builder) {
        List<PresentedProduct> presentedProducts = receipt.getPresentedProducts();
        for (PresentedProduct product : presentedProducts) {
            builder.append(String.format(GIFT_FORMAT, product.getName(), numberFormat(product.getQuantity())));
        }
    }

    private static void appendPurchaseProductInfo(List<Product> products, Receipt receipt, StringBuilder builder) {
        List<RequestItem> requestItems = receipt.getRequestItems();
        for (RequestItem requestItem : requestItems) {
            addProductInfo(products, requestItem, builder);
        }
    }

    private static void addProductInfo(List<Product> products, RequestItem requestItem, StringBuilder builder) {
        for (Product product : products) {
            if (product.getName().equals(requestItem.getName())) {
                builder.append(String.format(PRODUCT_FORMAT, requestItem.getName(),
                        numberFormat(requestItem.getQuantity()),
                        numberFormat(product.getPrice() * requestItem.getQuantity())));
                break;
            }
        }
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

    public static void printInputAdd(String name) {
        System.out.printf(ADD_MESSAGE, name);
    }

    public static void printInputNonePromotion(String name, int remainRequestSize) {
        System.out.printf(NONE_PROMOTION_MESSAGE, name, numberFormat(remainRequestSize));
    }

    public static void printInputMembershipDiscount() {
        System.out.println(MEMBERSHIP_DISCOUNT_MESSAGE);
    }

    public static void printInputRepurchase() {
        System.out.println(REPURCHASE_MESSAGE);
    }
}
