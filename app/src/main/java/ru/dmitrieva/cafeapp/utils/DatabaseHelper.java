package ru.dmitrieva.cafeapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ru.dmitrieva.cafeapp.data.Product;
import ru.dmitrieva.cafeapp.data.ProductCategory;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productsInfoManager";

    private interface CategoryTableColumns extends BaseColumns {

        String KEY_CATEGORY = "category";
        String TABLE_CATEGORIES = "categories";
        String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES
                + "(" + _ID + " INTEGER PRIMARY KEY,"
                + KEY_CATEGORY + " TEXT" + ")";
    }

    private interface ProductTableColumns extends BaseColumns {

        String KEY_CATEGORY_ID = "category_id";
        String KEY_TITLE = "title";
        String KEY_VOLUME = "volume";
        String KEY_INGREDIENTS = "ingredients";
        String KEY_PRICE = "price";
        String KEY_IMAGE_URL = "image_url";
        String TABLE_PRODUCTS = "products";
        String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS
                + "(" + _ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_VOLUME + " TEXT,"
                + KEY_INGREDIENTS + " TEXT,"
                + KEY_PRICE + " INTEGER,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_CATEGORY_ID + " INTEGER,"
                + " FOREIGN KEY (" + KEY_CATEGORY_ID + ") REFERENCES " + CategoryTableColumns.TABLE_CATEGORIES + "(" + CategoryTableColumns._ID + "));";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoryTableColumns.CREATE_TABLE_CATEGORIES);
        db.execSQL(ProductTableColumns.CREATE_TABLE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoryTableColumns.TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + ProductTableColumns.TABLE_PRODUCTS);
        onCreate(db);
    }

    public void clearDatabase() {
        String clearTableWeekDays = "DELETE FROM " + CategoryTableColumns.TABLE_CATEGORIES;
        String clearTableSections = "DELETE FROM " + ProductTableColumns.TABLE_PRODUCTS;
        getReadableDatabase().execSQL(clearTableWeekDays);
        getReadableDatabase().execSQL(clearTableSections);
    }

    public void addData(List<ProductCategory> productCategoryData) {
        clearDatabase();
        SQLiteDatabase database = this.getWritableDatabase();

        for (ProductCategory productCategory : productCategoryData) {
            ContentValues productCategoryValues = new ContentValues();
            productCategoryValues.put(CategoryTableColumns.KEY_CATEGORY, productCategory.getTitle());
            long categoryID = database.insert(CategoryTableColumns.TABLE_CATEGORIES, null, productCategoryValues);

            for (Product products : productCategory.getProductList()) {
                ContentValues productValues = new ContentValues();
                productValues.put(ProductTableColumns.KEY_TITLE, products.getTitle());
                productValues.put(ProductTableColumns.KEY_VOLUME, products.getVolume());
                productValues.put(ProductTableColumns.KEY_INGREDIENTS, products.getIngredients());
                productValues.put(ProductTableColumns.KEY_PRICE, products.getPrice());
                productValues.put(ProductTableColumns.KEY_IMAGE_URL, products.getImageURL());
                productValues.put(ProductTableColumns.KEY_CATEGORY_ID, categoryID);

                database.insert(ProductTableColumns.TABLE_PRODUCTS, null, productValues);
            }
        }
    }

    public List<ProductCategory> getData() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<ProductCategory> productData = new ArrayList<>();

        Cursor categoryCursor = database.query(CategoryTableColumns.TABLE_CATEGORIES, null, null, null,
                null, null, null);

        for (categoryCursor.moveToFirst(); !categoryCursor.isAfterLast(); categoryCursor.moveToNext()) {
            long categoryId = categoryCursor.getLong(categoryCursor.getColumnIndex(CategoryTableColumns._ID));
            String categoryTitle = categoryCursor.getString(categoryCursor.getColumnIndex(CategoryTableColumns.KEY_CATEGORY));

            List<Product> list = new ArrayList<>();
            Cursor productCursor = database.query(ProductTableColumns.TABLE_PRODUCTS,
                    null,
                    ProductTableColumns.KEY_CATEGORY_ID + " = " + categoryId,
                    null,
                    null,
                    null,
                    null);

            for (productCursor.moveToFirst(); !productCursor.isAfterLast(); productCursor.moveToNext()) {
                String title = productCursor.getString(productCursor.getColumnIndex(ProductTableColumns.KEY_TITLE));
                String volume = productCursor.getString(productCursor.getColumnIndex(ProductTableColumns.KEY_VOLUME));
                String ingredients = productCursor.getString(productCursor.getColumnIndex(ProductTableColumns.KEY_INGREDIENTS));
                int price = productCursor.getInt(productCursor.getColumnIndex(ProductTableColumns.KEY_PRICE));
                String imageUrl = productCursor.getString(productCursor.getColumnIndex(ProductTableColumns.KEY_IMAGE_URL));

                list.add(new Product(title, volume, ingredients, price, imageUrl));
            }

            productData.add(new ProductCategory(categoryTitle, list));
        }

        return productData;
    }
}



