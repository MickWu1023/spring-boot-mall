package com.example.demo.Dao.impl;

import com.example.demo.Constant.ProductQueryParms;
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
    public Integer countProduct(ProductQueryParms productQueryParms) {
        String  sql = "select count(*) from product  where 1=1";
        Map<String,Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,productQueryParms);
        Integer total = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParms productQueryParms) {
        String sql ="select product_id,product_name, category, image_url, price, stock, description," +
                " created_date, last_modified_date " +
                "from product where 1=1";
        Map<String,Object> map = new HashMap<>();
        //查詢條件
        sql = addFilteringSql(sql,map,productQueryParms);
        //排序
        sql = sql+ " order by " + productQueryParms.getOrderby() + " "+productQueryParms.getSort();
        //分頁
        sql = sql+ " Limit :limit offset :offset";
        map.put("limit",productQueryParms.getLimit());
        map.put("offset",productQueryParms.getOffset());
        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowmapper());
        return productList ;
    }
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

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "delete from product where product_id =:productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    private String addFilteringSql(String sql , Map<String,Object> map, ProductQueryParms productQueryParms) {
        if(productQueryParms.getCategory()!=null){
            sql = sql+ " and category=:category";
            map.put("category",productQueryParms.getCategory().name());
        }
        if(productQueryParms.getSearch()!=null){
            sql = sql+ " and product_name LIKE :search";
            map.put("search","%"+productQueryParms.getSearch()+"%" );
        }

        return sql;
    }

}
