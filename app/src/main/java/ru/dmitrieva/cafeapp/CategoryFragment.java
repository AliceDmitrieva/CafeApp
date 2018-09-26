package ru.dmitrieva.cafeapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    String title;

    public CategoryFragment(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.content_fragment, null);

        RecyclerView recyclerView = view.findViewById(R.id.contentFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new RecyclerAdapter(this.getActivity(), getProducts()));

        return view;
    }

    private ArrayList<Product> getProducts() {
        ArrayList<Product>  products = new ArrayList<>();
        products.add(new Product("Ветчина и грибы", "350 р", R.drawable.pizza_1));
        products.add(new Product("Цыпленок", "450 р", R.drawable.pizza_2));
        products.add(new Product("Овощи и грибы", "400 р", R.drawable.pizza_3));

        return products;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
