package ru.dmitrieva.cafeapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
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

    protected Product(Parcel in) {
        id = in.readInt();
        title = in.readString();
        price = in.readString();
        imageURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeString(imageURL);
    }

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
}

