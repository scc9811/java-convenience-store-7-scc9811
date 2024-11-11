package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.entity.*;
import store.util.ParseUtil;
import store.validator.BooleanTypeValidator;
import store.validator.ParseValidator;
import store.view.InputView;
import store.view.OutputView;

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

    public List<Product> getProducts() {
        List<String> productsInfo = readProductsInfo();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i < productsInfo.size(); i++) {
            addProduct(products, productsInfo.get(i));
        }
        return products;
    }

    private void addProduct(List<Product> products, String productInfo) {
        List<String> tokens = List.of(productInfo.split(","));
        products.add(
                new Product(tokens.get(0), Integer.parseInt(tokens.get(1)),
                        Integer.parseInt(tokens.get(2)), tokens.get(3)));
    }

    public List<Promotion> getPromotions() {
        List<String> promotionsInfo = readPromotionsInfo();
        List<Promotion> promotions = new ArrayList<>();
        for (int i = 1; i < promotionsInfo.size(); i++) {
            addPromotion(promotions, promotionsInfo.get(i));
        }
        return promotions;
    }

    private void addPromotion(List<Promotion> promotions, String promotionInfo) {
        List<String> tokens = List.of(promotionInfo.split(","));
        promotions.add(
                new Promotion(tokens.get(0), Integer.parseInt(tokens.get(1)),
                        Integer.parseInt(tokens.get(2)), tokens.get(3), tokens.get(4)));
    }

    public List<RequestItem> getRequestItems(String purchase) {
        List<String> tokens = List.of(purchase.split(","));
        ParseValidator.validateTokens(tokens);
        List<RequestItem> requestItems = new ArrayList<>();
        for (String token : tokens) {
            addRequestItem(requestItems, token.substring(1, token.length() - 1));
        }
        return requestItems;
    }

    private void addRequestItem(List<RequestItem> requestItems, String purchaseInfo) {
        List<String> purchaseTokens = List.of(purchaseInfo.split("-"));
        requestItems.add(new RequestItem(purchaseTokens.get(0),
                Integer.parseInt(purchaseTokens.get(1))));
    }

    public void calculatePromotionProduct(Product promotionProduct, Promotion promotion,
                                          RequestItem requestItem, Receipt receipt) {
        int calculateQuantity = Math.min(requestItem.getQuantity(), promotionProduct.getQuantity());
        receipt.totalPurchaseAmount += promotionProduct.getPrice() * calculateQuantity;
        int bundle = promotion.getBuy() + promotion.getGet();
        int presentedProductCount = calculateQuantity / bundle;
        updateReceipt(receipt, bundle, presentedProductCount, calculateQuantity, promotionProduct.getPrice());
        addPresentedProduct(promotionProduct, receipt, presentedProductCount);
        increasePurchasedCount(promotionProduct, receipt, calculateQuantity);
    }

    private void updateReceipt(Receipt receipt, int bundle, int presentedProductCount,
                               int calculateQuantity, int price) {
        receipt.promotionalAmount += bundle * presentedProductCount * price;
        receipt.nonPromotionalAmount += calculateQuantity % bundle * price;
        receipt.eventDisCount += presentedProductCount * price;
    }

    private void addPresentedProduct(Product promotionProduct, Receipt receipt, int productCount) {
        if (productCount > 0) {
            receipt.getPresentedProducts().add(new PresentedProduct(promotionProduct.getName(), productCount));
        }
    }

    private void increasePurchasedCount(Product promotionProduct, Receipt receipt, int calculateQuantity) {
        Map<String, Integer> purchasedCount = receipt.getPurchasedCount();
        int lastCount = purchasedCount.get(promotionProduct.getName());
        purchasedCount.put(promotionProduct.getName(), lastCount + calculateQuantity);
        promotionProduct.minusQuantity(calculateQuantity);
    }

    public void calculateNormalProduct(Product normalProduct, RequestItem requestItem, Receipt receipt) {
        if (receipt.getPurchasedCount().get(requestItem.getName()) == requestItem.getQuantity()) {
            return;
        }
        int lastCount = receipt.getPurchasedCount().get(normalProduct.getName());
        int calculateQuantity = requestItem.getQuantity() - lastCount;
        updateReceipt(receipt, normalProduct.getPrice() * calculateQuantity);
        receipt.getPurchasedCount().put(normalProduct.getName(), lastCount + calculateQuantity);
        normalProduct.minusQuantity(calculateQuantity);
    }

    private void updateReceipt(Receipt receipt, int plusAmount) {
        receipt.totalPurchaseAmount += plusAmount;
        receipt.nonPromotionalAmount += plusAmount;
    }

    public boolean containsPromotion(List<Product> products, List<Promotion> promotions, RequestItem requestItem) {
        Product promotionProduct = getPromotionProduct(products, requestItem.getName());
        if (promotionProduct != null) {
            Promotion promotion = getPromotion(promotions, promotionProduct.getPromotion());
            if (!(today.isBefore(promotion.getStartDate()) || today.isAfter(promotion.getEndDate()))) {
                return true;
            }
        }
        return false;
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
        int priceToApplyMembership = receipt.nonPromotionalAmount;
        int disCountPrice = Math.min((int) Math.round(priceToApplyMembership * 0.3), 8000);
        receipt.setMembershipDiscount(disCountPrice);
    }

    public void addNormalProduct(List<Product> products) {
        for (int i = 0; i < products.size() - 1; i++) {
            Product currentProduct = products.get(i);
            Product nextProduct = products.get(i + 1);
            if (currentProduct.getPromotion() != null && !currentProduct.getName().equals(nextProduct.getName())) {
                addProduct(products, i, currentProduct);
                i += 1;
            }
        }
    }

    private void addProduct(List<Product> products, int i, Product currentProduct) {
        products.add(i + 1, new Product(currentProduct.getName(),
                currentProduct.getPrice(), 0, "null"));
    }

    public void treatRequest(List<Product> products, List<Promotion> promotions,
                             List<RequestItem> requestItems, Receipt receipt) {
        for (RequestItem requestItem : requestItems) {
            eachRequestItem(products, promotions, requestItem, receipt);
        }
    }

    private void eachRequestItem(List<Product> products, List<Promotion> promotions,
                                 RequestItem requestItem, Receipt receipt) {
        if (containsPromotion(products, promotions, requestItem)) {
            sellPromotionProduct(products, promotions, requestItem, receipt);
        }
        sellNormalProduct(products, requestItem, receipt);
    }

    private void sellPromotionProduct(List<Product> products, List<Promotion> promotions,
                                      RequestItem requestItem, Receipt receipt) {
        Product promotionProduct = getPromotionProduct(products, requestItem.getName());
        Promotion promotion = getPromotion(promotions, promotionProduct.getPromotion());
        int bundle = promotion.getBuy() + promotion.getGet();
        int requestSize = requestItem.getQuantity();
        sellPromotionProductByBundle(promotionProduct, promotion, requestItem, receipt, bundle, requestSize);
    }

    private void sellPromotionProductByBundle(Product promotionProduct, Promotion promotion,
                                              RequestItem requestItem, Receipt receipt, int bundle, int requestSize) {
        boolean add = addPromotionProduct(promotionProduct, requestItem, bundle, requestSize);
        int tmp = receipt.promotionalAmount;
        calculatePromotionProduct(promotionProduct, promotion, requestItem, receipt);
        int remainRequestCount = remainRequestCount(requestItem, receipt);
        int nonPromotionCount = (requestSize - remainRequestCount) % bundle;
        int nonPromotionAmount = nonPromotionCount * promotionProduct.getPrice();
        boolean okPromotion = tmp != receipt.promotionalAmount;
        if (okPromotion && add) {
            nonPromotionCount = 0;
        }
        removeNormalProduct(requestItem, receipt, nonPromotionCount, nonPromotionAmount, bundle, promotionProduct, okPromotion);
    }

    private void removeNormalProduct(RequestItem requestItem, Receipt receipt, int nonPromotionCount,
                                     int nonPromotionAmount, int bundle, Product product, boolean okPromotion) {
        int remainRequestSize = remainRequestCount(requestItem, receipt);
        if (okPromotion && (bundle > 2 && nonPromotionCount != 0)) {
            OutputView.printInputNonePromotion(requestItem.getName(), remainRequestSize + nonPromotionCount);
            askRemoval(requestItem, remainRequestSize, nonPromotionCount, nonPromotionAmount, receipt, product);
        }
    }

    private void askRemoval(RequestItem requestItem, int remainRequestSize,
                            int nonPromotionCount, int nonPromotionAmount, Receipt receipt, Product product) {
        String nonPromotionInput = isRemovalProduct();
        boolean purchaseRegularPrice = ParseUtil.booleanParse(nonPromotionInput);
        if (!purchaseRegularPrice) {
            updateMinusReceipt(requestItem,remainRequestSize + nonPromotionCount, receipt, nonPromotionAmount, product);
            Map<String, Integer> purchasedCount = receipt.getPurchasedCount();
            purchasedCount.put(requestItem.getName(), purchasedCount.get(requestItem.getName()) - nonPromotionCount);
        }
    }

    private static void updateMinusReceipt(RequestItem item, int size, Receipt receipt,
                                           int nonPromotionAmount, Product product) {
        item.minusQuantity(size);
        product.plusQuantity(size);
        receipt.totalPurchaseAmount -= nonPromotionAmount;
        receipt.promotionalAmount -= nonPromotionAmount;
        receipt.nonPromotionalAmount -= nonPromotionAmount;
    }

    private String isRemovalProduct() {
        try {
            String nonPromotionInput = InputView.getUserInput();
            BooleanTypeValidator.validate(nonPromotionInput);
            return nonPromotionInput;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return isRemovalProduct();
        }
    }

    private boolean addPromotionProduct(Product promotionProduct, RequestItem requestItem,
                                     int bundle, int requestSize) {
        if (promotionProduct.getQuantity() >= requestSize + 1 && requestSize % bundle == bundle - 1) {
            OutputView.printInputAdd(promotionProduct.getName());
            return repeatAskPromotionProductAddition(requestItem);
        }
        return false;
    }

    private boolean repeatAskPromotionProductAddition(RequestItem requestItem) {
        try {
            return askPromotionProductAddition(requestItem);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return askPromotionProductAddition(requestItem);
        }
    }

    private boolean askPromotionProductAddition(RequestItem requestItem) {
        String addInput = isPromotionProductionAddition();
        boolean isGiftSelected = ParseUtil.booleanParse(addInput);
        if (isGiftSelected) {
            requestItem.plusQuantity(1);
        }
        return isGiftSelected;
    }

    private String isPromotionProductionAddition() {
        try {
            String addInput = InputView.getUserInput();
            BooleanTypeValidator.validate(addInput);
            return addInput;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return isPromotionProductionAddition();
        }
    }

    private void sellNormalProduct(List<Product> products, RequestItem requestItem, Receipt receipt) {
        // 일반 상품 계산
        Product normalProduct = getNomalProduct(products, requestItem.getName());
        calculateNormalProduct(normalProduct, requestItem, receipt);
    }

    private List<String> readProductsInfo() {
        return fileReader.readFile("products.md");
    }

    private List<String> readPromotionsInfo() {
        return fileReader.readFile("promotions.md");
    }
}
