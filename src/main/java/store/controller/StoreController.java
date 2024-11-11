package store.controller;

import store.handler.StoreHandler;

public class StoreController {
    private final StoreHandler storeHandler;
    public StoreController() {
        this.storeHandler = new StoreHandler();
    }
    public void run() {
        storeHandler.handleInitialize();
        storeHandler.handleRequestProduct();
        storeHandler.handleCalculation();
        storeHandler.handlePrintReceipt();
        storeHandler.handleRepurchase();
    }
}
