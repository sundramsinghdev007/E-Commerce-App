package com.sundram.shardhafertilizer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomCartAdapter extends RecyclerView.Adapter<CustomCartAdapter.ViewHolder> {

    private Context mContext;
    private List<addToCartCunstructor> listViewproductCart;

    public CustomCartAdapter(Context mContext, List<addToCartCunstructor> listViewproductCart) {
        this.mContext = mContext;
        this.listViewproductCart = listViewproductCart;
    }

    @NonNull
    @Override
    public CustomCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.single_cart_product_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomCartAdapter.ViewHolder viewHolder, int i) {
        addToCartCunstructor mdata = listViewproductCart.get(i);
        viewHolder.textViewProductName.setText(mdata.getProductName());
        viewHolder.textProductDescription.setText(mdata.getDescription());
        viewHolder.textQuantity.setText(mdata.getQuantity());
        viewHolder.textTotalPrice.setText(mdata.getPrice());
    }

    @Override
    public int getItemCount() {
        return listViewproductCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductName, textProductDescription, textQuantity, textTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textProductName);
            textProductDescription = itemView.findViewById(R.id.textProductDescription);
            textQuantity = itemView.findViewById(R.id.textQuantity);
            textTotalPrice = itemView.findViewById(R.id.textTotalPrice);

        }
    }
}
