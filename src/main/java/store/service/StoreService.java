package store.service;

import store.entity.FileReader;

import java.util.List;

public class StoreService {
    private final FileReader fileReader;
    public StoreService() {
        this.fileReader = new FileReader();
    }

    private List<String> readProductsInfo() {
        System.out.println(fileReader.readFile("products.md"));
        return fileReader.readFile("products.md");
    }

    private List<String> readPromotionsInfo() {
        return fileReader.readFile("promotions.md");
    }
}
