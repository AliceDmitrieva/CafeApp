package ru.dmitrieva.cafeapp;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.dmitrieva.cafeapp.data.Product;
import ru.dmitrieva.cafeapp.data.ProductCategory;
import ru.dmitrieva.cafeapp.utils.NetworkUtils;

public class ProductsRequestAsyncTask extends AsyncTask<Void, Void, List<ProductCategory>> {

    private Context context;
    private OnProductsRequestListener onProductsRequestListener;
    private Exception occurredError;

    public ProductsRequestAsyncTask(Context context, OnProductsRequestListener listener) {
        this.context = context;
        this.onProductsRequestListener = listener;
    }

    public interface OnProductsRequestListener {
        void onProductsRequestSuccess(List<ProductCategory> result);
        void onProductsRequestError(Exception error);
    }

    @Override
    protected List<ProductCategory> doInBackground(Void... arg) {
        String url = context.getResources().getString(R.string.server_url);
        String jsonString = NetworkUtils.readUrl(url);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, List<Product>>>() {
        }.getType();
        try {
            Map<String, List<Product>> productsMap = gson.fromJson(jsonString, type);

            List<ProductCategory> productCategoryList;
            productCategoryList = productsMap.entrySet().stream()
                    .map(entry -> new ProductCategory(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            return productCategoryList;
        } catch (Exception e) {
            occurredError = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProductCategory> result) {
        if (occurredError == null) {
            onProductsRequestListener.onProductsRequestSuccess(result);
        } else {
            onProductsRequestListener.onProductsRequestError(occurredError);
        }
    }
}
