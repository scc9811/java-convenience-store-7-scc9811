package store.validator;

import store.entity.Product;
import store.entity.RequestItem;

import java.util.List;

public class ProductValidator {
    private ProductValidator() {
    }

    public static void validateShortage(List<RequestItem> requestItems, List<Product> products) {
        for (RequestItem requestItem : requestItems) {
            int productTotalCount = 0;
            for (Product product : products) {
                if (requestItem.getName().equals(product.getName())) {
                    productTotalCount += product.getQuantity();
                }
            }
            if (productTotalCount < requestItem.getQuantity()) {
                throw new IllegalArgumentException(ErrorMessage.INSUFFICIENT_PRODUCT.getMessage());
            }
        }
    }
}
