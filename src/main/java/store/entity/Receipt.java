package store.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receipt {
    private final List<RequestItem> requestItems;
    private final Map<String, Integer> purchasedCount;
    public int totalPay, eventDisCount, membershipDiscount;
    public Receipt(List<RequestItem> requestItems) {
        this.requestItems = requestItems;
        this.totalPay = 0;
        this.eventDisCount = 0;
        this.membershipDiscount = 0;
        this.purchasedCount = new HashMap<>();
        for (RequestItem requestItem : requestItems) {
            purchasedCount.put(requestItem.getName(), 0);
        }
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

    public int getTotalPay() {
        return totalPay;
    }

    public int getEventDisCount() {
        return eventDisCount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public void setMembershipDiscount(int membershipDiscount) {
        this.membershipDiscount = membershipDiscount;
    }
}
