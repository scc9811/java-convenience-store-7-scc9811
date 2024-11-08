package store.service;

import store.entity.FileReader;
import store.entity.Product;

import java.util.ArrayList;
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

    public List<Product> getProducts() {
        List<String> productsInfo = readProductsInfo();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i < productsInfo.size(); i++) {
            List<String> tokens = List.of(productsInfo.get(i).split(","));
            products.add(
                    new Product(tokens.get(0), Integer.parseInt(tokens.get(1)),
                            Integer.parseInt(tokens.get(2)), tokens.get(3)));
        }
        return products;
    }
}
