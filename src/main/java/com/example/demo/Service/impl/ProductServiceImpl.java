package com.example.demo.Service.impl;

import com.example.demo.Constant.ProductCategory;
import com.example.demo.Constant.ProductQueryParms;
import com.example.demo.Dao.ProductDao;
import com.example.demo.Model.Product;
import com.example.demo.Service.ProductService;
import com.example.demo.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }

    @Override
    public List<Product> getProducts(ProductQueryParms  productQueryParms  ) {
        return productDao.getProducts(productQueryParms);
    }

    @Override
    public Integer countProduct(ProductQueryParms productQueryParms) {
        return  productDao.countProduct(productQueryParms);
    }
}
