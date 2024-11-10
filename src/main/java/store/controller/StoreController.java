package store.controller;

import store.entity.Product;
import store.entity.Promotion;
import store.entity.Receipt;
import store.entity.RequestItem;
import store.service.StoreService;
import store.util.ParseUtil;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class StoreController {
    private final StoreService storeService;
    public StoreController() {
        this.storeService = new StoreService();
    }
    public void run() {
        OutputView.printWelcomeMessage();
        List<Product> products = storeService.getProducts();
        List<Promotion> promotions = storeService.getPromotions();
        OutputView.printProductsInfo(products);
        OutputView.printInputPurchase();
        String purchase = InputView.getUserInput();
        purchase = ParseUtil.removeSpace(purchase);
        List<RequestItem> requestItems = storeService.getRequestItems(purchase);
        Receipt receipt = new Receipt(requestItems);
        for (RequestItem requestItem : requestItems) {
            // 프로모션 재고가 있는 경우 -> 프로모션 상품 먼저 소진
            if (storeService.containsPromotion(products, promotions, requestItem)) {
                Product promotionProduct = storeService.getPromotionProduct(products, requestItem.getName());
                Promotion promotion = storeService.getPromotion(promotions, promotionProduct.getPromotion());
                int bundle = promotion.getBuy() + promotion.getGet();
                int requestSize = requestItem.getQuantity();
                // 1. 프로모션 상품이 충분한 경우
                // 2. (배수-1) 의 수량을 만족하는 경우
                // 프로모션 상품 추가여부 묻는 경우는 계산 완료된 것. ( 이 경우만 따로 고려하지 말고, request item+1 이후에 계산할것. )
                if (promotionProduct.getQuantity() >= requestSize && requestSize % bundle == bundle - 1) {
                    OutputView.printInputAdd();
                    String addInput = InputView.getUserInput();
                    boolean isGiftSelected = ParseUtil.booleanParse(addInput);
                    if (isGiftSelected) {
                        requestItem.plusQuantity(1);
                    }
                }
                // 프로모션 상품 계산
                storeService.calculatePromotionProduct(promotionProduct, promotion, requestItem, receipt);

                // 일반 상품으로 계산해야 하는 상품이 남는 경우 -> 입출력
                int remainRequestSize = storeService.remainRequestCount(requestItem, receipt);
                if (remainRequestSize > 0) {
                    OutputView.printInputNonePromotion(requestItem.getName(), remainRequestSize);
                    String NonePromotionInput = InputView.getUserInput();
                    boolean purchaseRegularPrice = ParseUtil.booleanParse(NonePromotionInput);
                    // none promotion product 포함시키지 않는 경우 -> 해당 사이즈 만큼 제외.
                    if (!purchaseRegularPrice) {
                        requestItem.minusQuantity(remainRequestSize);
                    }
                }
            }
            // 일반 상품 계산
            Product normalProduct = storeService.getNomalProduct(products, requestItem.getName());
            storeService.calculateNormalProduct(normalProduct, requestItem, receipt);

        }
        // 멤버십 할인 적용 여부 -> 입출력
        OutputView.printInputMembershipDiscount();
        String isMembershipInput = InputView.getUserInput();
        boolean isMembership = ParseUtil.booleanParse(isMembershipInput);
        if (isMembership) {
            storeService.membershipDiscount(receipt);
        }

        // 영수증 출력
        OutputView.printReceipt(products, receipt);


        OutputView.printInputRepurchase();
        String repurchaseInput = InputView.getUserInput();
        boolean isRepurchase = ParseUtil.booleanParse(repurchaseInput);






    }
}
