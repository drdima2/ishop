package net.devstudy.ishop.model;

import javafx.print.Collation;
import net.devstudy.ishop.Constants;
import net.devstudy.ishop.entity.Product;
import net.devstudy.ishop.exception.ValidationException;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = -4009487502653715461L;

    private Map<Integer,ShoppingCartItem> products = new HashMap<>();
    private int totalCount=0;
    private BigDecimal totalCost=BigDecimal.ZERO;

    public void addProduct(Product product, int count){
        validateShoppingCartItem(product.getId());
        ShoppingCartItem shoppingCartItem = products.get(product.getId());
        if (shoppingCartItem == null){
            validateProductCount(count);
            shoppingCartItem = new ShoppingCartItem(product,count);
            products.put(product.getId(),shoppingCartItem);
        }else {
            validateProductCount(count + shoppingCartItem.getCount());
            shoppingCartItem.setCount(shoppingCartItem.getCount()+count);
        }
        refreshStatistics();

    }



    public void removeProduct(Integer idProduct, int count){
        ShoppingCartItem shoppingCartItem = products.get(idProduct);
        if (shoppingCartItem != null){
            if (shoppingCartItem.getCount() > count){
                shoppingCartItem.setCount(shoppingCartItem.getCount() - count);
            }else {
                products.remove(idProduct);
            }
            refreshStatistics();
        }
    }

    public Collection<ShoppingCartItem> getItems(){
        return products.values();
    }

    public int getTotalCount(){
        return totalCount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    private void validateProductCount(int count) {
        if (count>Constants.MAX_PRODUCT_COUNT_PER_SHOPPING_CART){
            throw new ValidationException("Limit for product count reached: count=" + count);
        }
    }


    private void validateShoppingCartSize(int idProduct){
        if (products.size() > Constants.MAX_PRODUCTS_PER_SHOPPING_CART ||
                (products.size() == Constants.MAX_PRODUCTS_PER_SHOPPING_CART && !products.containsKey(idProduct)))
                {
                    throw new ValidationException("Limit for Shopping Cart size reached size=" + products.size());

        }
    }


    private void refreshStatistics() {
        totalCount=0;
        totalCost=BigDecimal.ZERO;
        for (ShoppingCartItem shoppingCartItem:getItems()){
            totalCount+=shoppingCartItem.getCount();
            totalCost=totalCost.add(shoppingCartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(shoppingCartItem.getCount())));

        }
    }

    private void validateShoppingCartItem(Integer id) {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShoppingCart{");
        sb.append("products=").append(products);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", totalCost=").append(totalCost);
        sb.append('}');
        return sb.toString();
    }
}
