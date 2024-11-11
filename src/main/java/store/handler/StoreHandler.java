package store.handler;

import store.entity.Product;
import store.entity.Promotion;
import store.entity.Receipt;
import store.entity.RequestItem;
import store.service.StoreService;
import store.util.ParseUtil;
import store.validator.BooleanTypeValidator;
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
    }

    private void askRequestProduct() {
        try {
            String purchase = InputView.getUserInput();
            requestItems = storeService.getRequestItems(purchase);
            validateProduct();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            askRequestProduct();
        }
    }

    private void validateProduct() {
        ProductValidator.validateProductExist(requestItems, products);
        ProductValidator.validateShortage(requestItems, products);
    }

    public void handleCalculation() {
        receipt = new Receipt(requestItems);
        storeService.treatRequest(products, promotions, requestItems, receipt);
        handleMembershipDiscount();
    }

    public void handleMembershipDiscount() {
        OutputView.printInputMembershipDiscount();
        askMembership();
    }

    private void askMembership() {
        String isMembershipInput = repeatAskMemberShip();
        boolean isMembership = ParseUtil.booleanParse(isMembershipInput);
        if (isMembership) {
            storeService.membershipDiscount(receipt);
        }
    }

    private String repeatAskMemberShip() {
        try {
            String isMembershipInput = InputView.getUserInput();
            BooleanTypeValidator.validate(isMembershipInput);
            return isMembershipInput;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return repeatAskMemberShip();
        }
    }

    public void handlePrintReceipt() {
        OutputView.printReceipt(products, receipt);
    }

    public boolean handleRepurchase() {
        OutputView.printInputRepurchase();
        return isRepurchase();
    }

    private boolean isRepurchase() {
        try {
            String repurchaseInput = InputView.getUserInput();
            BooleanTypeValidator.validate(repurchaseInput);
            return ParseUtil.booleanParse(repurchaseInput);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return isRepurchase();
        }
    }
}
