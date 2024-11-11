package store.entity;

public class PresentedProduct {
    private String name;
    private int quantity;

    public PresentedProduct(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
