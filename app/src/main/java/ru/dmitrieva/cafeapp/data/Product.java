package ru.dmitrieva.cafeapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Product implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private static List<Product> buyList = new ArrayList<>();
    private String title;
    private String volume;
    private String ingredients;
    private int price;
    private String imageURL;
    private int selectedProductCount;
    private int selectedProductCost;

    public Product(String title, String volume, String ingredients, int price, String imageURL) {
        this.title = title;
        this.volume = volume;
        this.ingredients = ingredients;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Product(String title, String volume, String ingredients, int price, String imageURL, int selectedProductCount, int selectedProductCost) {
        this.title = title;
        this.volume = volume;
        this.ingredients = ingredients;
        this.price = price;
        this.imageURL = imageURL;
        this.selectedProductCount = selectedProductCount;
        this.selectedProductCost = selectedProductCost;
    }

    protected Product(Parcel in) {
        title = in.readString();
        volume = in.readString();
        ingredients = in.readString();
        price = in.readInt();
        imageURL = in.readString();
        selectedProductCount = in.readInt();
        selectedProductCost = in.readInt();
    }

    public static List<Product> getBuyList() {
        return buyList;
    }

    public static void addProductToBuyList(Product product, int productCount, int productCost) {
        Product product_temp = null;
        if (!buyList.isEmpty()) {
            for (Product p : buyList) {
                if (p.getTitle().equals(product.getTitle())) {
                    product_temp = p;
                    product_temp.increaseSelectedProductCount(productCount);
                    product_temp.increaseSelectedProductCost(productCost);
                }
            }
        }
        if (product_temp == null) {
            buyList.add(product);
            product.setSelectedProductCount(productCount);
            product.setSelectedProductPrice(productCost);
        }
    }

    public static void removeProductFromBuyList(Product product) {
        buyList.remove(product);
    }

    public static int getTotalPrice() {
        int totalPrice = 0;
        for (Product p : buyList) {
            totalPrice += p.getSelectedProductCost();
        }
        return totalPrice;
    }

    public static int getTotalCount() {
        int totalCount = 0;
        for (Product p : buyList) {
            totalCount += p.getSelectedProductCount();
        }
        return totalCount;
    }

    public int getSelectedProductCount() {
        return selectedProductCount;
    }

    public void setSelectedProductCount(int selectedProductCount) {
        this.selectedProductCount = selectedProductCount;
    }

    public int getSelectedProductCost() {
        return selectedProductCost;
    }

    public void setSelectedProductPrice(int selectedProductCost) {
        this.selectedProductCost = selectedProductCost;
    }

    public String getTitle() {
        return title;
    }

    public String getVolume() {
        return volume;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void increaseSelectedProductCount(int selectedProductCount) {
        this.selectedProductCount += selectedProductCount;
    }

    public void increaseSelectedProductCost(int selectedProductCost) {
        this.selectedProductCost += selectedProductCost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(volume);
        dest.writeString(ingredients);
        dest.writeInt(price);
        dest.writeString(imageURL);
        dest.writeInt(selectedProductCount);
        dest.writeInt(selectedProductCost);
    }
}
