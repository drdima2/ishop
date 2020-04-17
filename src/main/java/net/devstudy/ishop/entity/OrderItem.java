package net.devstudy.ishop.entity;

import java.math.BigInteger;

public class OrderItem extends AbstractEntity<Long>{
    private Long idOrder;
    private Product product;
    private int count;


    public OrderItem(Product product, int count) {
        super();
        this.product = product;
        this.count = count;
    }

    public OrderItem() {
        super();
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Product getProduct() {
        return product;
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
