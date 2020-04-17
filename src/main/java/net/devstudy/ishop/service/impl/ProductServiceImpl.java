package net.devstudy.ishop.service.impl;

import net.devstudy.ishop.entity.Category;
import net.devstudy.ishop.entity.Producer;
import net.devstudy.ishop.entity.Product;
import net.devstudy.ishop.exception.InternalServerErrorException;
import net.devstudy.ishop.form.SearchForm;
import net.devstudy.ishop.jdbc.JDBCUtils;
import net.devstudy.ishop.jdbc.ResultSetHandler;
import net.devstudy.ishop.jdbc.ResultSetHandlerFactory;
import net.devstudy.ishop.service.ProductService;
import net.devstudy.ishop.util.SearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


class ProductServiceImpl implements ProductService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final DataSource dataSource;

    private static final ResultSetHandler<List<Product>> productsResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(
                    ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);

    private static final ResultSetHandler<List<Category>> categoryResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(
                    ResultSetHandlerFactory.CATEGORY_RESULT_SET_HANDLER);

    private static final ResultSetHandler<List<Producer>> producerResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(
                    ResultSetHandlerFactory.PRODUCER_RESULT_SET_HANDLER);


    private static final ResultSetHandler<Integer> countResultSetHandler =
            ResultSetHandlerFactory.getCountResultSetHandler();


    public ProductServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    @Override
    public List<Product> listAllProducts(int page, int limit) {
        try(Connection c = dataSource.getConnection()){
            int offset = (page-1)*limit;
            String sql="SELECT p.* , c.name as category , pr.name as producer FROM product p, producer pr, category c " +
                    "WHERE c.id=p.id_category and pr.id=p.id_producer " +
                    "LIMIT ? " +
                    "OFFSET ?";
            return JDBCUtils.select(c,sql,productsResultSetHandler,limit, offset);
        }
        catch (SQLException e){
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(),e);
        }

    }

    @Override
    public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
        try(Connection c = dataSource.getConnection()){
            int offset = (page-1)*limit;
            String sql="SELECT p.* , c.name as category , pr.name as producer FROM product p, producer pr, category c " +
                    "WHERE c.id=p.id_category and pr.id=p.id_producer " +
                    "and c.url=?" +
                    "LIMIT ? " +
                    "OFFSET ?";
            return JDBCUtils.select(c,sql,productsResultSetHandler,categoryUrl,limit, offset);
        }
        catch (SQLException e){
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(),e);
        }
    }

    @Override
    public List<Category> listAllCategories() {
        try(Connection c = dataSource.getConnection()){

            String sql="SELECT * FROM category order by id";

            return JDBCUtils.select(c,sql,categoryResultSetHandler);
        }
        catch (SQLException e){
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(),e);
        }
    }

    @Override
    public List<Producer> listAllProducer() {
        try(Connection c = dataSource.getConnection()){

            String sql="SELECT * FROM producer order by name";

            return JDBCUtils.select(c,sql,producerResultSetHandler);
        }
        catch (SQLException e){
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(),e);
        }
    }

    @Override
    public int countAllProducts() {
        try(Connection c = dataSource.getConnection()){

            String sql="SELECT count(*) FROM product";
            return JDBCUtils.select(c,sql,countResultSetHandler);
        }
        catch (SQLException e){
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(),e);
        }
    }

    @Override
    public int countProductByCategory(String categoryUrl) {
        try(Connection c = dataSource.getConnection()){

            String sql="SELECT count(p.*) FROM product p, category c " +
                    "WHERE c.id=p.id_category " +
                    "and c.url=?";
            return JDBCUtils.select(c,sql,countResultSetHandler,categoryUrl);
        }
        catch (SQLException e){
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(),e);
        }
    }

    @Override
    public List<Product> listProductsBySearchForm(SearchForm form, int page, int limit) {
        try(Connection c = dataSource.getConnection()){
            int offset = (page-1)*limit;



            SearchQuery sq = buildSearchQuery("p.*, c.name as category, pr.name as producer ", form);
            sq.getSql().append(" order by p.id limit ? offset ?");
            sq.getParams().add(limit);
            sq.getParams().add(offset);
            LOGGER.debug("search query = {} with params={}",sq.getSql(),sq.getParams());
            return JDBCUtils.select(c,sq.getSql().toString(),productsResultSetHandler,sq.getParams().toArray());
        }
        catch (SQLException e){
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(),e);
        }
    }

    protected SearchQuery buildSearchQuery(String selectedFields,SearchForm form){
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(selectedFields).append(" FROM product p, producer pr, category c " +
                "WHERE c.id=p.id_category and pr.id=p.id_producer and (" +
                "p.name ilike ? or p.description ilike ? )");
        params.add("%" + form.getQuery() + "%");
        params.add("%" + form.getQuery() + "%");
        JDBCUtils.populatedSqlAndParams(sql,params,form.getCategories(), "c.id = ?");
        JDBCUtils.populatedSqlAndParams(sql,params,form.getProducers(), "pr.id = ?");
        return new SearchQuery(sql,params);
    }

    @Override
    public int countProductsBySearchForm(SearchForm searchForm) {
        try(Connection c = dataSource.getConnection()){

            String sql="SELECT count(*) FROM product p, producer pr, category c " +
                    "WHERE c.id=p.id_category and pr.id=p.id_producer and " +
                    "p.name ilike ? or p.description ilike ? ";
            return JDBCUtils.select(c,sql,countResultSetHandler,
                    "%"+searchForm.getQuery()+"%","%"+ searchForm.getQuery()+"%");
        }
        catch (SQLException e){
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(),e);
        }
    }
}
