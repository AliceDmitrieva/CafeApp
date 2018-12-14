package ru.dmitrieva.cafeapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.dmitrieva.cafeapp.R;
import ru.dmitrieva.cafeapp.adapters.ProductCardAdapter;
import ru.dmitrieva.cafeapp.data.Product;
import ru.dmitrieva.cafeapp.data.ProductCategory;

public class CategoryFragment extends Fragment implements ProductCardAdapter.OnEntryClickListener {

    private static final String ARGUMENT_PRODUCT_LIST = "product list";
    private OnProductCardClickListener onProductCardClickListener;

    public static CategoryFragment newInstance(ProductCategory category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_PRODUCT_LIST, new ArrayList<>(category.getProductList()));
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnProductCardClickListener {
        void onProductCardClick(Product product);
        void onButtonOrderFromCardClick(Product product);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.onProductCardClickListener = (OnProductCardClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement OnProductCardClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onProductCardClickListener = null;
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

        ArrayList<Product> productList = arguments.getParcelableArrayList(ARGUMENT_PRODUCT_LIST);
        if (productList == null) {
            throw new IllegalArgumentException("product list == null");
        }

        ProductCardAdapter adapter = new ProductCardAdapter(productList);
        recyclerView.setAdapter(adapter);
        adapter.setOnEntryClickListener(this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return view;
    }

    @Override
    public void onCardClick(Product product) {
        if (product != null) {
            onProductCardClickListener.onProductCardClick(product);
        }
    }

    @Override
    public void onButtonOrderClick(Product product) {
        if (product != null) {
            onProductCardClickListener.onButtonOrderFromCardClick(product);
        }
    }
}

