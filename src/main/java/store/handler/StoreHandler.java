package store.handler;

import store.entity.Product;
import store.entity.Promotion;
import store.entity.Receipt;
import store.entity.RequestItem;
import store.service.StoreService;
import store.util.ParseUtil;
import store.validator.ProductValidator;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class StoreHandler {
    private final StoreService storeService;
    List<Product> products;
    List<Promotion> promotions;
    List<RequestItem> requestItems;
    Receipt receipt;

    public StoreHandler() {
        this.storeService = new StoreService();
    }

    public void handleInitialize() {
        OutputView.printWelcomeMessage();
        products = storeService.getProducts();
        storeService.addNormalProduct(products);
        promotions = storeService.getPromotions();
    }

    public void handleRequestProduct() {
        OutputView.printProductsInfo(products);
        OutputView.printInputPurchase();
        askRequestProduct();
//        try {
//            askRequestProduct();
//        }
//        catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//            askRequestProduct();
//        }
    }

    private void askRequestProduct() {
        try {
            String purchase = InputView.getUserInput();
            purchase = ParseUtil.removeSpace(purchase);
            requestItems = storeService.getRequestItems(purchase);
            ProductValidator.validateProductExist(requestItems, products);
            ProductValidator.validateShortage(requestItems, products);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            askRequestProduct();
        }
    }

    public void handleCalculation() {
        receipt = new Receipt(requestItems);
        storeService.treatRequest(products, promotions, requestItems, receipt);
        handleMembershipDiscount();
    }

    public void handleMembershipDiscount() {
        OutputView.printInputMembershipDiscount();
        try {
            askMembership();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            askMembership();
        }
    }

    private void askMembership() {
        String isMembershipInput = InputView.getUserInput();
        boolean isMembership = ParseUtil.booleanParse(isMembershipInput);
        if (isMembership) {
            storeService.membershipDiscount(receipt);
        }
    }

    public void handlePrintReceipt() {
        OutputView.printReceipt(products, receipt);
    }

    public void handleRepurchase() {
        OutputView.printInputRepurchase();
        String repurchaseInput = InputView.getUserInput();
        boolean isRepurchase = ParseUtil.booleanParse(repurchaseInput);
        if (isRepurchase) {
            // file initialize 제외하고 다시 시작.
        }
    }
}
