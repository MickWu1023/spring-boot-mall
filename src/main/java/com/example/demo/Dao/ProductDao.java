package com.example.demo.Dao;

import com.example.demo.Constant.ProductCategory;
import com.example.demo.Constant.ProductQueryParms;
import com.example.demo.Model.Product;
import com.example.demo.dto.ProductRequest;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductQueryParms productQueryParms);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
    Integer countProduct(ProductQueryParms productQueryParms);
}
