package ru.dmitrieva.cafeapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.dmitrieva.cafeapp.R;
import ru.dmitrieva.cafeapp.data.Product;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    private static final String ARGUMENT_PRODUCT_TITLE = "product title";
    private static final String ARGUMENT_PRODUCT_VOLUME = "product volume";
    private static final String ARGUMENT_PRODUCT_INGREDIENTS = "product ingredients";
    private static final String ARGUMENT_PRODUCT_PRICE = "product price";
    private static final String ARGUMENT_PRODUCT_IMAGE = "product image";

    private OnButtonOrderClickListener onButtonOrderClickListener;
    private Button buttonMinus;
    private Button buttonPlus;
    private TextView countTextView;
    private TextView priceTextView;

    private String productTitle;
    private String productVolume;
    private String productIngredients;
    private int productPrice;
    private String productImage;
    private int productCount;
    private int selectedProductCost;

    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_PRODUCT_TITLE, product.getTitle());
        args.putString(ARGUMENT_PRODUCT_VOLUME, product.getVolume());
        args.putString(ARGUMENT_PRODUCT_INGREDIENTS, product.getIngredients());
        args.putInt(ARGUMENT_PRODUCT_PRICE, product.getPrice());
        args.putString(ARGUMENT_PRODUCT_IMAGE, product.getImageURL());
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnButtonOrderClickListener {
        void onButtonOrderClick(Product product);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.onButtonOrderClickListener = (OnButtonOrderClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement OnButtonOrderClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onButtonOrderClickListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.product_detail_fragment, null);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        productTitle = arguments.getString(ARGUMENT_PRODUCT_TITLE);
        if (productTitle == null) {
            throw new IllegalArgumentException("product title == null");
        }

        productVolume = arguments.getString(ARGUMENT_PRODUCT_VOLUME);
        if (productVolume == null) {
            throw new IllegalArgumentException("product volume == null");
        }

        productIngredients = arguments.getString(ARGUMENT_PRODUCT_INGREDIENTS);
        if (productIngredients == null) {
            throw new IllegalArgumentException("product ingredients == null");
        }

        productPrice = arguments.getInt(ARGUMENT_PRODUCT_PRICE);
        if (productPrice == 0) {
            throw new IllegalArgumentException("product price == 0");
        }

        productImage = arguments.getString(ARGUMENT_PRODUCT_IMAGE);
        if (productImage == null) {
            throw new IllegalArgumentException("product image == null");
        }

        productCount = 1;

        TextView titleTextView = view.findViewById(R.id.product_title);
        titleTextView.setText(productTitle);

        TextView volumeTextView = view.findViewById(R.id.product_volume);
        volumeTextView.setText(productVolume);

        TextView ingredientsTextView = view.findViewById(R.id.product_ingredients);
        ingredientsTextView.setText(productIngredients);

        priceTextView = view.findViewById(R.id.product_price);
        priceTextView.setText(String.valueOf(productPrice));

        ImageView imageView = view.findViewById(R.id.product_image);
        Picasso.get().load(productImage).into(imageView);

        countTextView = view.findViewById(R.id.product_count);

        buttonMinus = view.findViewById(R.id.button_minus);
        buttonMinus.setOnClickListener(this);

        buttonPlus = view.findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(this);

        Button buttonOrder = view.findViewById(R.id.button_final_order);
        buttonOrder.setOnClickListener(this);

        return view;
    }

    public void reduceCount() {
        if (productCount == 1) {
            buttonMinus.setClickable(false);
        } else {
            buttonMinus.setClickable(true);
            --productCount;
            countTextView.setText(String.valueOf(productCount));
            changeCost();
        }
    }

    public void increaseCount() {
        ++productCount;
        countTextView.setText(String.valueOf(productCount));
        if (productCount == 1) {
            buttonMinus.setClickable(false);
        } else {
            buttonMinus.setClickable(true);
        }
        changeCost();

    }

    public void changeCost() {
        selectedProductCost = productPrice * productCount;
        priceTextView.setText(String.valueOf(selectedProductCost));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.button_minus):
                reduceCount();
                break;
            case (R.id.button_plus):
                increaseCount();
                break;
            case (R.id.button_final_order):
                if ((productCount) == 1) {
                    selectedProductCost = productPrice;
                }
                Product product = new Product(productTitle, productVolume, productIngredients, productPrice, productImage, productCount, selectedProductCost);
                onButtonOrderClickListener.onButtonOrderClick(product);
                break;
        }
    }
}

