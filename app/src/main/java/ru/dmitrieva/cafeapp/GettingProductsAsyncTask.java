package ru.dmitrieva.cafeapp;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GettingProductsAsyncTask extends AsyncTask<Void, Void, List<ProductCategory>> {

    @Override
    protected List<ProductCategory> doInBackground(Void... arg) {
        String url = "https://my-json-server.typicode.com/AliceDmitrieva/CafeAppServer/db";
        String jsonString = readUrl(url);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, List<Product>>>() {
        }.getType();
        Map<String, List<Product>> productsMap = gson.fromJson(jsonString, type);

        List<ProductCategory> productCategoryList = new ArrayList<>();;

//        for (Map.Entry<String, List<Product>> entry : productsMap.entrySet()) {
//            String title = entry.getKey();
//            List<Product> productList = entry.getValue();
//            productCategoryList.add(new ProductCategory(title, productList));
//        }

        productCategoryList = productsMap.entrySet().stream()
                .map(entry -> new ProductCategory(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        System.out.println(productCategoryList.toString());
        return productCategoryList;
    }

    @Override
    protected void onPostExecute(List<ProductCategory> result) {

    }

    private static String readUrl(String urlString) {
        BufferedReader reader = null;
        try {
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            try {
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
