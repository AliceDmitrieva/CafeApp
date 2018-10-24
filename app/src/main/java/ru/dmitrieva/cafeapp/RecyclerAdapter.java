package ru.dmitrieva.cafeapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProductViewHolder> {

    private Context context;
    private String categoryTitle;
    private List<Product> productList = new ArrayList<>();


    public RecyclerAdapter(Context context, String categoryTitle, ArrayList<Product> productList) {
        this.context = context;
        this.categoryTitle = categoryTitle;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_model, null);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.title.setText(productList.get(position).getTitle());
        holder.price.setText(productList.get(position).getPrice());
    //  holder.imageView.setImageResource(products.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
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
