package store.validator;

import store.entity.Product;
import store.entity.RequestItem;

import java.util.List;

public class ProductValidator {
    private ProductValidator() {
    }

    public static void validateProductExist(List<RequestItem> requestItems, List<Product> products) {
        for (RequestItem requestItem : requestItems) {
            boolean exist = isExist(products, requestItem);
            if (!exist) {
                throw new IllegalArgumentException(ErrorMessage.NON_EXISTENT_PRODUCT.getMessage());
            }
        }
    }

    private static boolean isExist(List<Product> products, RequestItem requestItem) {
        for (Product product : products) {
            if (requestItem.getName().equals(product.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void validateShortage(List<RequestItem> requestItems, List<Product> products) {
        for (RequestItem requestItem : requestItems) {
            int productTotalCount = totalCount(requestItem, products);
            if (productTotalCount < requestItem.getQuantity()) {
                throw new IllegalArgumentException(ErrorMessage.INSUFFICIENT_PRODUCT.getMessage());
            }
        }
    }

    private static int totalCount(RequestItem requestItem, List<Product> products) {
        int productTotalCount = 0;
        for (Product product : products) {
            if (requestItem.getName().equals(product.getName())) {
                productTotalCount += product.getQuantity();
            }
        }
        return productTotalCount;
    }
}
