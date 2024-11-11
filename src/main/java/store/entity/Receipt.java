package store.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receipt {
    private final List<RequestItem> requestItems;
    private final List<PresentedProduct> presentedProducts;
    private final Map<String, Integer> purchasedCount;
    public int totalPurchaseAmount, eventDisCount, membershipDiscount;

    public Receipt(List<RequestItem> requestItems) {
        this.requestItems = requestItems;
        this.presentedProducts = new ArrayList<>();
        this.purchasedCount = new HashMap<>();
        for (RequestItem requestItem : requestItems) {
            purchasedCount.put(requestItem.getName(), 0);
        }
    }

    public int totalPayMoney() {
        return totalPurchaseAmount - (eventDisCount + membershipDiscount);
    }

    public int totalPurchasedProductsCount() {
        int sum = 0;
        for (RequestItem requestItem : requestItems) {
            sum += requestItem.getQuantity();
        }
        return sum;
    }

    public Map<String, Integer> getPurchasedCount() {
        return purchasedCount;
    }

    public List<RequestItem> getRequestItems() {
        return requestItems;
    }

    public int getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public int getEventDisCount() {
        return eventDisCount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public List<PresentedProduct> getPresentedProducts() {
        return presentedProducts;
    }

    public void setMembershipDiscount(int membershipDiscount) {
        this.membershipDiscount = membershipDiscount;
    }
}
