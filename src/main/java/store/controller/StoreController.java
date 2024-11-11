package store.controller;

import store.handler.StoreHandler;

public class StoreController {
    private final StoreHandler storeHandler;

    public StoreController() {
        this.storeHandler = new StoreHandler();
        storeHandler.handleInitialize();
    }

    public void run() {
        boolean RequestPurchase = true;
        while (RequestPurchase) {
            purchase();
            RequestPurchase = storeHandler.handleRepurchase();
        }
    }

    private void purchase() {
        storeHandler.handleRequestProduct();
        storeHandler.handleCalculation();
        storeHandler.handlePrintReceipt();
    }
}
