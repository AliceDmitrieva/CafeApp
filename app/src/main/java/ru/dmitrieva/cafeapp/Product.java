package ru.dmitrieva.cafeapp;

public class Product {
    private int id;
    private String title;
    private String price;
    private String imageURL;

    public Product(String title, String price, String imageURL) {
        this.title = title;
        this.price = price;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }
}

