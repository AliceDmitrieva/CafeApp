package ru.dmitrieva.cafeapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductCategory> CREATOR = new Parcelable.Creator<ProductCategory>() {
        @Override
        public ProductCategory createFromParcel(Parcel in) {
            return new ProductCategory(in);
        }

        @Override
        public ProductCategory[] newArray(int size) {
            return new ProductCategory[size];
        }
    };

    private String title;
    private List<Product> productList;

    public ProductCategory(String title, List<Product> productList) {
        this.title = title;
        this.productList = productList;
    }

    protected ProductCategory(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0x01) {
            productList = new ArrayList<Product>();
            in.readList(productList, Product.class.getClassLoader());
        } else {
            productList = null;
        }
    }

    public String getTitle() {
        return title;
    }

    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        if (productList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(productList);
        }
    }
}
