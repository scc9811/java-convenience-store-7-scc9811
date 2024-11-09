package store.entity;

public class RequestItem {
    private String name;
    private int quantity;

    public void plusQuantity(int size) {
        quantity += size;
    }

    public RequestItem(String name, int quantity) {
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
