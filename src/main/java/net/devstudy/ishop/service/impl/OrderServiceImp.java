package net.devstudy.ishop.service.impl;

import net.devstudy.ishop.entity.Product;
import net.devstudy.ishop.exception.InternalServerErrorException;
import net.devstudy.ishop.form.ProductForm;
import net.devstudy.ishop.jdbc.JDBCUtils;
import net.devstudy.ishop.jdbc.ResultSetHandler;
import net.devstudy.ishop.jdbc.ResultSetHandlerFactory;
import net.devstudy.ishop.model.ShoppingCart;
import net.devstudy.ishop.service.OrderService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class OrderServiceImp implements OrderService {

    private final DataSource dataSource;

    private static final ResultSetHandler<Product> productResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(
                    ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);

    public OrderServiceImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
        try(Connection c = dataSource.getConnection()){
            String sql="SELECT p.* , c.name as category , pr.name as producer FROM product p, producer pr, category c " +
                    "WHERE c.id=p.id_category and pr.id=p.id_producer " +
                    "and p.id=?" ;

            Product product= JDBCUtils.select(c,sql,productResultSetHandler,productForm.getIdProduct());
            if (product==null){
                throw new InternalServerErrorException("Product not found by id=" + productForm.getIdProduct());
            }
            shoppingCart.addProduct(product,productForm.getCount());
        }
        catch (SQLException e){
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(),e);
        }

    }
}
