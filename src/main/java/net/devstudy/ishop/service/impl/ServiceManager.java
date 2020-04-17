package net.devstudy.ishop.service.impl;

import net.devstudy.ishop.service.OrderService;
import net.devstudy.ishop.service.ProductService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;


public class ServiceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);

    public static ServiceManager getInstance(ServletContext context){
        ServiceManager instance = (ServiceManager) context.getAttribute("SERVICE_MANAGER");
        if (instance==null){
            instance = new ServiceManager(context);
            context.setAttribute("SERVICE_MANAGER", instance);
        }
        return instance;
    }



    private final Properties applicationProperties = new Properties();
    private final BasicDataSource dataSource;
    private ProductService productService;
    private OrderService orderService;

    public ProductService getProductService() {
        return productService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public String getApplicationProperty(String key){
        return  applicationProperties.getProperty(key);
    }

    public void close(){
        try {
            dataSource.close();
        } catch (SQLException e) {
            LOGGER.error("Close data source failed: " + e.getMessage(),e);
        }
    }

    private ServiceManager(ServletContext context) {
        loadApplicationProperties();
        dataSource = createDataSource();
        productService = new ProductServiceImpl(dataSource);
        orderService = new OrderServiceImp(dataSource);
    }

    private BasicDataSource createDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDefaultAutoCommit(false);
        dataSource.setRollbackOnReturn(true);
        dataSource.setDriverClassName(getApplicationProperty("db.driver"));
        dataSource.setUrl(getApplicationProperty("db.url"));
        dataSource.setUsername(getApplicationProperty("db.username"));
        dataSource.setPassword(getApplicationProperty("db.password"));
        dataSource.setInitialSize(Integer.parseInt(getApplicationProperty("db.pool.initSize")));
        dataSource.setMaxTotal(Integer.parseInt(getApplicationProperty("db.pool.maxSize")));
        return dataSource;
    }

    private void loadApplicationProperties(){
        try(InputStream in = ServiceManager.class.getClassLoader().getResourceAsStream("application.properties")){
            applicationProperties.load(in);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
