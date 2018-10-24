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

    private static final String ARGUMENT_CATEGORY_TITLE = "argument_category_title";
    private static final String ARGUMENT_PRODUCTLIST = "product_list";

    public static CategoryFragment newInstance(ProductCategory category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_CATEGORY_TITLE, category.getTitle());
        args.putParcelableArrayList(ARGUMENT_PRODUCTLIST, new ArrayList<>(category.getProductList()));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.content_fragment, null);
        RecyclerView recyclerView = view.findViewById(R.id.contentFragment);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        String categoryTitle = arguments.getString(ARGUMENT_CATEGORY_TITLE);
        if (categoryTitle == null) {
            throw new IllegalArgumentException("category title == null");
        }

        ArrayList<Product> productList = arguments.getParcelableArrayList(ARGUMENT_PRODUCTLIST);
        if (productList == null) {
            throw new IllegalArgumentException("product list is empty");
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacingItemDecoration(5, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new RecyclerAdapter(getActivity(), categoryTitle, productList));

        return view;
    }

    private int dpToPx(int dp) {
        Resources resources = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }
}

