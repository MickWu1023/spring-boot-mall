package com.example.demo.Dao;

import com.example.demo.Model.Product;
import com.example.demo.dto.ProductRequest;

public interface ProductDao {

    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
