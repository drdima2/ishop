package net.devstudy.ishop.listener;

import net.devstudy.ishop.model.ShoppingCart;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {
    public static ShoppingCart getCurrentShoppingCart(HttpServletRequest req) {
        ShoppingCart sessionShoppingCart = (ShoppingCart) req.getSession().getAttribute("shopping_cart");
        if (sessionShoppingCart ==null ) {
            ShoppingCart newShoppingCart = new ShoppingCart();
            req.getSession().setAttribute("shopping_cart",newShoppingCart);
            return newShoppingCart;
        }
        else{
            return sessionShoppingCart;
        }
    }
}
