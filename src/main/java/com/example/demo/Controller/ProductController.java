package com.example.demo.Controller;

import com.example.demo.Constant.ProductCategory;
import com.example.demo.Constant.ProductQueryParms;
import com.example.demo.Model.Product;
import com.example.demo.Service.ProductService;
import com.example.demo.dto.ProductRequest;
import com.example.demo.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
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
        //取得product list
        List<Product> productList =  productService.getProducts(productQueryParms);
        //取得product 總數
        Integer total = productService.countProduct(productQueryParms);
        //分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);
        return  ResponseEntity.status(HttpStatus.OK).body(page);
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
    @GetMapping("/users")
    public List<Map<String, String>> getUsers() {
        List<Map<String, String>> users = new ArrayList<>();
        users.add(Map.of("id", "1", "name", "Alice"));
        users.add(Map.of("id", "2", "name", "Bob"));
        return users;
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
