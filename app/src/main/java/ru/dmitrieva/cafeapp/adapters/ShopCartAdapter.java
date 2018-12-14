package ru.dmitrieva.cafeapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.dmitrieva.cafeapp.R;
import ru.dmitrieva.cafeapp.data.Product;

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ShoppingBasketViewHolder> {

    private List<Product> selectedProducts;
    private OnChangeResultListener onChangeResultListener;

    public ShopCartAdapter(ArrayList<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public interface OnChangeResultListener {
        void onChangeCost();
        void onChangeCount();
        void onCheckShopCart();
    }

    public void setOnChangeResultListener(OnChangeResultListener listener) {
        this.onChangeResultListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ShopCartAdapter.ShoppingBasketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_cart_product_model, parent, false);
        return new ShoppingBasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingBasketViewHolder holder, int position) {
        holder.bindProduct(selectedProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return selectedProducts.size();
    }

    public class ShoppingBasketViewHolder extends RecyclerView.ViewHolder {

        TextView title, selectedProductCount, selectedProductCost;
        ImageView image;
        Button buttonRemove, buttonMinus, buttonPlus;
        Product product;

        public ShoppingBasketViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_title);
            selectedProductCount = itemView.findViewById(R.id.selected_product_count);
            selectedProductCost = itemView.findViewById(R.id.selected_product_price);
            image = itemView.findViewById(R.id.product_image);
            buttonMinus = itemView.findViewById(R.id.button_minus);
            buttonPlus = itemView.findViewById(R.id.button_plus);
            buttonRemove = itemView.findViewById(R.id.button_remove);
        }

        void bindProduct(Product product) {
            this.product = product;
            onChangeResultListener.onChangeCost();
            title.setText(product.getTitle());
            Picasso.get().load(product.getImageURL()).into(image);
            selectedProductCount.setText(String.valueOf(product.getSelectedProductCount()));
            selectedProductCost.setText(String.valueOf(product.getSelectedProductCost()));

            buttonRemove.setOnClickListener(v -> {
                Product.removeProductFromBuyList(product);
                ((ViewManager) v.getParent()).removeView(v);
                ((ViewManager) title.getParent()).removeView(title);
                ((ViewManager) selectedProductCount.getParent()).removeView(selectedProductCount);
                ((ViewManager) selectedProductCost.getParent()).removeView(selectedProductCost);
                ((ViewManager) image.getParent()).removeView(image);
                ((ViewManager) buttonMinus.getParent()).removeView(buttonMinus);
                ((ViewManager) buttonPlus.getParent()).removeView(buttonPlus);
                onChangeResultListener.onChangeCost();
                onChangeResultListener.onChangeCount();
                onChangeResultListener.onCheckShopCart();
            });

            buttonMinus.setOnClickListener(v -> {
                int count = product.getSelectedProductCount();
                if (count == 1) {
                    buttonMinus.setClickable(false);
                } else {
                    buttonMinus.setClickable(true);
                    selectedProductCount.setText(String.valueOf(--count));
                    int finalCost = product.getPrice() * count;
                    selectedProductCost.setText(String.valueOf(product.getPrice() * count));
                    product.setSelectedProductCount(count);
                    product.setSelectedProductPrice(finalCost);
                    onChangeResultListener.onChangeCost();
                    onChangeResultListener.onChangeCount();
                }
            });

            buttonPlus.setOnClickListener(v -> {
                int count = product.getSelectedProductCount();
                selectedProductCount.setText(String.valueOf(++count));
                if (count == 1) {
                    buttonMinus.setClickable(false);
                } else {
                    buttonMinus.setClickable(true);
                }
                int finalCost = product.getPrice() * count;
                selectedProductCost.setText(String.valueOf(finalCost));
                product.setSelectedProductCount(count);
                product.setSelectedProductPrice(finalCost);
                onChangeResultListener.onChangeCost();
                onChangeResultListener.onChangeCount();
            });
        }
    }
}
