package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.entity.*;
import store.validator.ParseValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreService {
    private final FileReader fileReader;
    private final LocalDate today;

    public StoreService() {
        this.fileReader = new FileReader();
        this.today = DateTimes.now().toLocalDate();
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
        ParseValidator.validateTokens(tokens);
        List<RequestItem> requestItems = new ArrayList<>();
        for (String token : tokens) {
            List<String> purchaseToken = List.of(token.substring(1, token.length() - 1).split("-"));
            requestItems.add(new RequestItem(purchaseToken.get(0),
                    Integer.parseInt(purchaseToken.get(1))));
        }
        return requestItems;
    }

    // 가능한 최대한 프로모션 상품 결제.
    public void calculatePromotionProduct(Product promotionProduct, Promotion promotion, RequestItem requestItem, Receipt receipt) {
        int calculateQuantity = Math.min(requestItem.getQuantity(), promotionProduct.getQuantity());
        receipt.totalPurchaseAmount += promotionProduct.getPrice() * calculateQuantity;
        int bundle = promotion.getBuy() + promotion.getGet();
        int presentedProductCount = calculateQuantity / bundle;
        receipt.eventDisCount += promotionProduct.getPrice() * presentedProductCount;
        if (presentedProductCount > 0) {
            receipt.getPresentedProducts().add(new PresentedProduct(promotionProduct.getName(), presentedProductCount));
        }
        Map<String, Integer> purchasedCount = receipt.getPurchasedCount();
        int lastCount = purchasedCount.get(promotionProduct.getName());
        purchasedCount.put(promotionProduct.getName(), lastCount + calculateQuantity);
        promotionProduct.minusQuantity(calculateQuantity);
    }

    public void calculateNormalProduct(Product normalProduct, RequestItem requestItem, Receipt receipt) {
        // 이미 구매 완료한 경우 바로 Return
        if (receipt.getPurchasedCount().get(requestItem.getName()) == requestItem.getQuantity()) {
            return;
        }
        // 구매하야 할 수량 남은 경우 계산
        Map<String, Integer> purchasedCount = receipt.getPurchasedCount();
        int lastCount = purchasedCount.get(normalProduct.getName());
        int calculateQuantity = requestItem.getQuantity() - lastCount;
        receipt.totalPurchaseAmount += normalProduct.getPrice() * calculateQuantity;
        purchasedCount.put(normalProduct.getName(), lastCount + calculateQuantity);
        normalProduct.minusQuantity(calculateQuantity);
    }

    public boolean containsPromotion(List<Product> products, List<Promotion> promotions, RequestItem requestItem) {
        Product promotionProduct = getPromotionProduct(products, requestItem.getName());
        if (promotionProduct == null) {
            return false;
        }
        Promotion promotion = getPromotion(promotions, promotionProduct.getPromotion());
        if (today.isBefore(promotion.getStartDate()) || today.isAfter(promotion.getEndDate())) {
            return false;
        }
        return true;
    }

    public Product getNomalProduct(List<Product> products, String name) {
        for (Product product : products) {
            if (product.getName().equals(name) && product.getPromotion() == null) return product;
        }
        return null;
    }

    public Product getPromotionProduct(List<Product> products, String name) {
        for (Product product : products) {
            if (product.getName().equals(name) && product.getPromotion() != null && product.getQuantity() > 0) {
                return product;
            }
        }
        return null;
    }

    public Promotion getPromotion(List<Promotion> promotions, String name) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) return promotion;
        }
        return null;
    }

    public int remainRequestCount(RequestItem requestItem, Receipt receipt) {
        return requestItem.getQuantity() - receipt.getPurchasedCount().get(requestItem.getName());
    }

    public void membershipDiscount(Receipt receipt) {
        int priceToApplyMembership = receipt.getTotalPurchaseAmount() - receipt.getEventDisCount();
        int disCountPrice = Math.min((int) Math.round(priceToApplyMembership * 0.3), 8000);
        receipt.setMembershipDiscount(disCountPrice);
    }

    public void addNormalProduct(List<Product> products) {
        for (int i = 0; i < products.size() - 1; i++) {
            Product currentProduct = products.get(i);
            Product nextProduct = products.get(i + 1);
            if (currentProduct.getPromotion() != null && !currentProduct.getName().equals(nextProduct.getName())) {
                products.add(i + 1, new Product(currentProduct.getName(), currentProduct.getPrice(), 0, "null"));
                i += 1;
            }
        }
    }
}
