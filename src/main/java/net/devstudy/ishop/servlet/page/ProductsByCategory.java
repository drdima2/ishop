package net.devstudy.ishop.servlet.page;

import net.devstudy.ishop.Constants;
import net.devstudy.ishop.entity.Product;
import net.devstudy.ishop.servlet.AbstractController;
import net.devstudy.ishop.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/products/*")
public class ProductsByCategory extends AbstractController {
    private static final long serialVersionUID = -5555569701157251380L;
    private static final int SUBSTRING_INDEX = "/products".length();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
        List<Product> products = getProductService().listProductsByCategory(categoryUrl,1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
        int totalCount = getProductService().countProductByCategory(categoryUrl);
        req.setAttribute("pageCount",getPageCount(totalCount,Constants.MAX_PRODUCTS_PER_HTML_PAGE));
        req.setAttribute("products",products);
        req.setAttribute("selectedCategoryUrl",categoryUrl);
        RoutingUtils.forwardToPage("products.jsp",req,resp);
    }
}
