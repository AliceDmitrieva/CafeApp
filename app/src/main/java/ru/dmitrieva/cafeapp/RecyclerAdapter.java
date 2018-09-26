package ru.dmitrieva.cafeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProductViewHolder> {

    Context context;
    ArrayList<Product> products;

    public RecyclerAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_model, null);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.title.setText(products.get(position).getTitle());
        holder.price.setText(products.get(position).getPrice());
        holder.imageView.setImageResource(products.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView title, price;
        ImageView imageView;
        Button button;

        public ProductViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.card_view_title);
            price = itemView.findViewById(R.id.card_view_price);
            imageView = itemView.findViewById(R.id.card_view_image);
            button = itemView.findViewById(R.id.button);
        }
    }
}
