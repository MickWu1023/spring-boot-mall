package com.example.demo.Service;

import com.example.demo.Model.Product;
import com.example.demo.dto.ProductRequest;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct (ProductRequest productRequest);
}
