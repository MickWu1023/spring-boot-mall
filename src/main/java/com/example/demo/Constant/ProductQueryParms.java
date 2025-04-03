package com.example.demo.Constant;

public class ProductQueryParms {
    private ProductCategory category;

    public ProductCategory getCategory() {
        return category;
    }


    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    private String search;

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public  String orderby;
    public  String sort;
}
