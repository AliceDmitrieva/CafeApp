package ru.dmitrieva.cafeapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.dmitrieva.cafeapp.PopUpWindowActivity;
import ru.dmitrieva.cafeapp.R;
import ru.dmitrieva.cafeapp.adapters.ShopCartAdapter;
import ru.dmitrieva.cafeapp.data.Product;

public class ShopCartFragment extends Fragment implements ShopCartAdapter.OnChangeResultListener {

    private static final String ARGUMENT_SELECTED_PRODUCTS = "selected products";

    private static ArrayList<Product> productList = new ArrayList<>();
    public OnChangeCountTextListener onChangeCountTextListener;

    private RelativeLayout shopCartMessage;
    private LinearLayout finalCostPanel;
    private TextView finalCostTextView;

    public static ShopCartFragment newInstance(List<Product> selectedProducts) {
        ShopCartFragment fragment = new ShopCartFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_SELECTED_PRODUCTS, new ArrayList<>(selectedProducts));
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnChangeCountTextListener {
        void onChangeCountText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.shop_cart_fragment, null);
        RecyclerView recyclerView = view.findViewById(R.id.shop_cart_container);

        finalCostTextView = view.findViewById(R.id.final_cost);
        finalCostPanel = view.findViewById(R.id.final_cost_panel);
        shopCartMessage = view.findViewById(R.id.shop_cart_message);

        Button buttonFinalOrder = view.findViewById(R.id.button_final_order);
        buttonFinalOrder.setOnClickListener(v -> startActivity(new Intent(getActivity(), PopUpWindowActivity.class)));

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        productList = arguments.getParcelableArrayList(ARGUMENT_SELECTED_PRODUCTS);
        if (productList == null) {
            throw new IllegalArgumentException("product list == null");
        }

        onCheckShopCart();
        ShopCartAdapter adapter = new ShopCartAdapter(productList);
        recyclerView.setAdapter(adapter);
        adapter.setOnChangeResultListener(this);

        return view;
    }

    @Override
    public void onChangeCost() {
        finalCostTextView.setText(String.valueOf(Product.getTotalPrice()));
    }

    @Override
    public void onChangeCount() {
        if (onChangeCountTextListener != null) {
            onChangeCountTextListener.onChangeCountText();
        }
    }

    @Override
    public void onCheckShopCart() {
        if (Product.getBuyList().isEmpty()) {
            shopCartMessage.setVisibility(View.VISIBLE);
            finalCostPanel.setVisibility(View.GONE);
        } else {
            shopCartMessage.setVisibility(View.GONE);
            finalCostPanel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.onChangeCountTextListener = (OnChangeCountTextListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement OnChangeCountTextListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onChangeCountTextListener = null;
    }
}



