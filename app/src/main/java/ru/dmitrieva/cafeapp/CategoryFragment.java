package ru.dmitrieva.cafeapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacingItemDecoration(5, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new RecyclerAdapter(this.getActivity(), getProducts()));

        return view;
    }

    private int dpToPx(int dp) {
        Resources resources = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

    private ArrayList<Product> getProducts() {
        ArrayList<Product>  products = new ArrayList<>();
  //     if (title.equals("Пицца")) {
  //         products.add(new Product("Ветчина и грибы", "350 р", R.drawable.pizza_1));
  //         products.add(new Product("Цыпленок", "450 р", R.drawable.pizza_2));
  //         products.add(new Product("Овощи и грибы", "400 р", R.drawable.pizza_3));
  //     }

  //     else if (title.equals("Салаты")) {
  //         products.add(new Product("Цезарь", "320 р", R.drawable.salad_caesar));
  //         products.add(new Product("Капрезе", "200 р", R.drawable.salad_caprese));
  //         products.add(new Product("Греческий", "200 р", R.drawable.salad_greek));
  //     }

  //     else if (title.equals("Паста")) {
  //         products.add(new Product("Болоньезе", "220 р", R.drawable.pasta_boloneze));
  //         products.add(new Product("Карбонара", "220 р", R.drawable.pasta_carbonara));
  //         products.add(new Product("Песто", "200 р", R.drawable.pasta_pesto));
  //     }

  //     else if (title.equals("Кофе")) {
  //         products.add(new Product("Капучино", "120 р", R.drawable.coffee_cappuccino));
  //         products.add(new Product("Эспрессо", "90 р", R.drawable.coffee_espresso));
  //         products.add(new Product("Латте", "130 р", R.drawable.cofee_latte));
  //     }

  //     else if (title.equals("Чай")) {
  //         products.add(new Product("Имбирный чай", "150 р", R.drawable.tea_ginger));
  //         products.add(new Product("Молочный улун", "160 р", R.drawable.tea_milkyoolong));
  //         products.add(new Product("Облепиховый чай", "140 р", R.drawable.tea_seabuckthorn));
  //     }

        return products;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
