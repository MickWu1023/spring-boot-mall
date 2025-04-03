package com.example.demo.Service;

import com.example.demo.Constant.ProductCategory;
import com.example.demo.Model.Product;
import com.example.demo.dto.ProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductCategory category ,String search);
    Product getProductById(Integer productId);
    Integer createProduct (ProductRequest productRequest);
    void updateProduct (Integer productId,ProductRequest productRequest);
    void deleteProductById (Integer productId);


}
