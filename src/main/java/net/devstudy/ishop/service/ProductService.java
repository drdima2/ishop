package net.devstudy.ishop.service;

import net.devstudy.ishop.entity.Category;
import net.devstudy.ishop.entity.Producer;
import net.devstudy.ishop.entity.Product;
import net.devstudy.ishop.form.SearchForm;

import java.util.List;

public interface ProductService {

    List<Product> listAllProducts(int page, int limit);

    int countAllProducts();

    List<Product> listProductsByCategory(String categoryUrl, int page, int limit);

    int countProductByCategory(String categoryUrl);

    List<Category> listAllCategories();

    List<Producer> listAllProducer();

    List<Product> listProductsBySearchForm(SearchForm searchForm, int page, int limit);

    int countProductsBySearchForm(SearchForm searchForm);

}
