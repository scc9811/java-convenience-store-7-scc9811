package store.entity;

import store.util.ParseUtil;

public class Product {
    private String name, promotion;
    private int price, quantity;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
        if (promotion.equals("null")) {
            this.promotion = null;
        }
    }

    public void plusQuantity(int size) {
        quantity += size;
    }

    public void minusQuantity(int size) {
        quantity -= size;
    }

    public String getName() {
        return name;
    }

    public String getPromotion() {
        return promotion;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String info() {
        String productInfo = "- " + name + " " + ParseUtil.numberFormat(price) + "원 ";
        if (quantity == 0) {
            productInfo = productInfo + "재고 없음 ";
        }
        if (quantity != 0) {
            productInfo = productInfo + ParseUtil.numberFormat(quantity) + "개 ";
        }
        if (promotion != null) {
            productInfo = productInfo + promotion;
        }
        return productInfo;
    }
}