package ru.dmitrieva.cafeapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.dmitrieva.cafeapp.R;
import ru.dmitrieva.cafeapp.data.Product;

public class ProductCardAdapter extends RecyclerView.Adapter<ProductCardAdapter.ProductViewHolder> {

    private List<Product> productList = new ArrayList<>();
    private OnEntryClickListener onEntryClickListener;

    public ProductCardAdapter(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public interface OnEntryClickListener {
        void onCardClick(Product product);
        void onButtonOrderClick(Product product);
    }

    public void setOnEntryClickListener(OnEntryClickListener listener) {
        this.onEntryClickListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_product_model, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bindProduct(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, price;
        ImageView image;
        Button button;
        Product product;

        public ProductViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.card_view_title);
            price = itemView.findViewById(R.id.card_view_price);
            image = itemView.findViewById(R.id.card_view_image);
            button = itemView.findViewById(R.id.card_view_button);
        }

        void bindProduct(Product product) {
            this.product = product;
            title.setText(product.getTitle());
            price.setText(String.valueOf(product.getPrice()));
            Picasso.get().load(product.getImageURL()).into(image);

            button.setOnClickListener(v -> {
                onEntryClickListener.onButtonOrderClick(product);
            });
        }

        @Override
        public void onClick(View v) {
            onEntryClickListener.onCardClick(product);
        }
    }
}
