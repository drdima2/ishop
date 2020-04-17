package net.devstudy.ishop.listener;

import net.devstudy.ishop.Constants;
import net.devstudy.ishop.entity.Category;
import net.devstudy.ishop.service.impl.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class IShopApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(IShopApplicationListener.class);
    private ServiceManager serviceManager;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            serviceManager = ServiceManager.getInstance(sce.getServletContext());
            ServletContext sc = sce.getServletContext();
            sc.setAttribute(Constants.CATEGORY_LIST,serviceManager.getProductService().listAllCategories());
            sc.setAttribute(Constants.PRODUCER_LIST,serviceManager.getProductService().listAllProducer());


        } catch (RuntimeException e) {
            LOGGER.error("Web application ishop init failed: " + e.getMessage(),e);
            throw e;
        }



    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
