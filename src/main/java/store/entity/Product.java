package store.entity;

public class Product {
    private String name, promotion;
    private int price, quantity;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
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
