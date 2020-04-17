package net.devstudy.ishop.servlet;

import net.devstudy.ishop.form.ProductForm;
import net.devstudy.ishop.form.SearchForm;
import net.devstudy.ishop.service.OrderService;
import net.devstudy.ishop.service.ProductService;
import net.devstudy.ishop.service.impl.ServiceManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractController extends HttpServlet {

    private static final long serialVersionUID = -7643277515098002783L;

    private ProductService productService;
    private OrderService orderService;

    @Override
    public final void init() throws ServletException {

        ServletContext servletContext = getServletContext();
        ServiceManager serviceManager = ServiceManager.getInstance(servletContext);
        productService = serviceManager.getProductService();
        orderService = ServiceManager.getInstance(getServletContext()).getOrderService();

    }

    public final ProductService getProductService() {
        return productService;
    }

    public final OrderService getOrderService() {
        return orderService;
    }

    public final int getPageCount(int totalCount, int itemsPerPage){
        int res = totalCount / itemsPerPage;
        if (res * itemsPerPage != totalCount){
            res++;
        }
        return res;
    }

    public final int getPage(HttpServletRequest req){
        try {
            return Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public final SearchForm createSearchForm(HttpServletRequest req){
        return new SearchForm(
                req.getParameter("query"),
                req.getParameterValues("category"),
                req.getParameterValues("producer"));


    }

    public final ProductForm createProductForm(HttpServletRequest req){
        return new ProductForm(
                Integer.parseInt(req.getParameter("idProduct")),
                Integer.parseInt(req.getParameter("count")));



    }
}
