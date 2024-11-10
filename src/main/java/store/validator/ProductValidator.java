package store.validator;

import store.entity.Product;
import store.entity.RequestItem;

import java.util.List;

public class ProductValidator {
    private ProductValidator() {
    }

    public static void validateProductExist(List<RequestItem> requestItems, List<Product> products) {
        for (RequestItem requestItem : requestItems) {
            boolean exist = false;
            for (Product product : products) {
                if (requestItem.getName().equals(product.getName())) {
                    exist = true;
                }
            }
            if (!exist) {
                throw new IllegalArgumentException(ErrorMessage.NON_EXISTENT_PRODUCT.getMessage());
            }
        }
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
