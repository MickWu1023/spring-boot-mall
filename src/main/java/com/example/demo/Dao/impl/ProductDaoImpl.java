package com.example.demo.Dao.impl;

import com.example.demo.Dao.ProductDao;
import com.example.demo.Model.Product;
import com.example.demo.Rowmapper.ProductRowmapper;
import com.example.demo.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql ="select product_id,product_name, category, image_url, price, stock, description," +
                " created_date, last_modified_date " +
                "from product where product_id =:productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowmapper());
        if(productList.size() >0)
        {
            return productList.get(0);
        }
        else {
            return null;
        }

    }

        @Override
        public Integer createProduct(ProductRequest productRequest) {
            String sql ="INSERT INTO product(product_name, category," +
                    " image_url, price, stock, description, created_date, " +
                    "last_modified_date ) values (:productName, :category," +
                    ":imageUrl,:price , :stock,:description,:createdDate," +
                    ":lastModifiedDate)" ;
            Map<String,Object> map = new HashMap<>();
            map.put("productName",productRequest.getProductName());
            map.put("category",productRequest.getCategory().toString());
            map.put("imageUrl",productRequest.getImageUrl());
            map.put("price",productRequest.getPrice());
            map.put("stock",productRequest.getStock());
            map.put("description",productRequest.getDescription());
            Date date = new Date();
            map.put("createdDate",date);
            map.put("lastModifiedDate",date);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
            int   productId = keyHolder.getKey().intValue();
            return productId;
        }

    @Override
    public void updateProduct(Integer productId,ProductRequest productRequest) {
        String sql ="Update product set product_name=:productName, category= :category, image_url = :imageUrl, price= :price,stock=:stock,description=:description ,last_modified_date=:lastModifiedDate where product_id=:productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        Date date = new Date();
        map.put("lastModifiedDate",date);
        namedParameterJdbcTemplate.update(sql,map);
    }
}
