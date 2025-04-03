package com.example.demo.Controller;

import com.example.demo.Constant.ProductCategory;
import com.example.demo.Constant.ProductQueryParms;
import com.example.demo.Model.Product;
import com.example.demo.Service.ProductService;
import com.example.demo.dto.ProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            //filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false)  String search,
            //sorting
            @RequestParam(defaultValue = "created_date") String orderby,
            @RequestParam(defaultValue = "desc") String sort,
            //pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
            ){
        ProductQueryParms  productQueryParms = new ProductQueryParms();
        productQueryParms.setCategory(category);
        productQueryParms.setSearch(search);
        productQueryParms.setOrderby(orderby);
        productQueryParms.setSort(sort);
        productQueryParms.setLimit(limit);
        productQueryParms.setOffset(offset);

        List<Product> productList =  productService.getProducts(productQueryParms);
        return  ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
            Product product = productService.getProductById(productId);
            if(product!=null){
                return ResponseEntity.status(HttpStatus.OK).body(product);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody  @Valid ProductRequest productRequest) {
        Integer productId  = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductRequest productRequest ,@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if(product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productId,productRequest);
        Product updateProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
