package ru.dmitrieva.cafeapp;

import java.util.List;

public class ProductCategory {

    private String title;
    private List<Product> productList;

    public ProductCategory(String title, List<Product> productList) {
        this.title = title;
        this.productList = productList;
    }
}
