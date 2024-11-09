package store.controller;

import store.entity.Product;
import store.entity.Promotion;
import store.service.StoreService;
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
    }
}
