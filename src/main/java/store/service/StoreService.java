package store.service;

import store.entity.FileReader;
import store.entity.Product;
import store.entity.Promotion;
import store.entity.RequestItem;

import java.util.ArrayList;
import java.util.List;

public class StoreService {
    private final FileReader fileReader;
    public StoreService() {
        this.fileReader = new FileReader();
    }

    private List<String> readProductsInfo() {
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

    public List<Promotion> getPromotions() {
        List<String> promotionsInfo = readPromotionsInfo();
        List<Promotion> promotions = new ArrayList<>();
        for (int i = 1; i < promotionsInfo.size(); i++) {
            List<String> tokens = List.of(promotionsInfo.get(i).split(","));
            promotions.add(
                    new Promotion(tokens.get(0), Integer.parseInt(tokens.get(1)),
                            Integer.parseInt(tokens.get(2)), tokens.get(3), tokens.get(4)));
        }
        return promotions;
    }

    public List<RequestItem> getRequestItems(String purchase) {
        List<String> tokens = List.of(purchase.split(","));
        List<RequestItem> requestItems = new ArrayList<>();
        for (String token : tokens) {
            List<String> purchaseToken = List.of(token.substring(1, token.length()-1).split("-"));
            requestItems.add(new RequestItem(purchaseToken.get(0).substring(1),
                    Integer.parseInt(purchaseToken.get(1).substring(0, purchaseToken.size()-1))));
        }
        return requestItems;
    }

    public boolean isShortage(List<RequestItem> requestItems, List<Product> products) {
        for (RequestItem requestItem : requestItems) {
            int productTotalCount = 0;
            for (Product product : products) {
                if (requestItem.getName().equals(product.getName())) {
                    productTotalCount += product.getQuantity();
                }
            }
            if (productTotalCount < requestItem.getQuantity()) {
                return false;
            }
        }
        return true;
    }
}
