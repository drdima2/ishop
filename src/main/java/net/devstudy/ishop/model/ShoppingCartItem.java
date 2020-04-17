package net.devstudy.ishop.model;

import net.devstudy.ishop.entity.Product;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable {

    private static final long serialVersionUID = 5641581374541152869L;

    private Product product;
    private int count;

    public ShoppingCartItem(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShoppingCartItem{");
        sb.append("product=").append(product);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
