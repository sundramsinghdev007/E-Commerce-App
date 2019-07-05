package com.sundram.shardhafertilizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyAdapter> {
    //public static final String product_Data_To_Description_URL = "https://eeragongoon001.000webhostapp.com/api.php";
    private Context mContext;
    private List<ProductName> mData;

    public RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public RecyclerViewAdapter(Context mContext, List<ProductName> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.single_product,viewGroup,false);
        MyAdapter viewAdapter = new MyAdapter(v);
        return viewAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter holder, int i) {
        ProductName product = mData.get(i);
            holder.textViewProductIds.setText(String.valueOf(product.getId()));

        //holder.textViewProductIds.setText(String.valueOf(product.getId()));
        holder.textViewName.setText(product.getProductName());
        holder.textViewProductContent.setText(product.getDescription());
        //holder.textViewProductPrice.setText(String.valueOf(product.getPrice()));
        //loading image from server url
        Glide.with(mContext).load(product.getProductPhoto()).into(holder.imageViewProduct);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewName, textViewProductContent, textViewProductPrice, textViewProductIds;
        private ImageView imageViewProduct;
        private Button btndesc;
        TextView textViewRateList;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            textViewProductIds = itemView.findViewById(R.id.productIds);
            textViewName=itemView.findViewById(R.id.textViewProductName);
            textViewProductContent = itemView.findViewById(R.id.textViewProductContent);
            //textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            imageViewProduct=itemView.findViewById(R.id.Product_Image);
            btndesc = itemView.findViewById(R.id.productDescription);
            textViewRateList = itemView.findViewById(R.id.rateList);
            textViewRateList.setOnClickListener(this);
            btndesc.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(btndesc.getId()==v.getId()){
                                        imageViewProduct.buildDrawingCache();
                                        Bitmap image = imageViewProduct.getDrawingCache();
                                        Bundle extras = new Bundle();
                                        extras.putParcelable("imageResource",image);

                                        Intent intent = new Intent(mContext,ProductDescription.class);
                                        //intent.putExtra("quantity",jsonObject.getString("quantity"));
                                        intent.putExtra("id",textViewProductIds.getText().toString());
                                        intent.putExtra("name",textViewName.getText().toString());
                                        intent.putExtra("content",textViewProductContent.getText().toString());
                                        //intent.putExtra("price",textViewProductPrice.getText().toString());
                                        intent.putExtras(extras);
                                        mContext.startActivity(intent);

                                        //Toast.makeText(mContext,"response: "+rateListData.toString(),Toast.LENGTH_LONG).show();

            }
            else if(textViewRateList.getId()==v.getId()){
                //Toast.makeText(mContext,"Rate List",Toast.LENGTH_LONG).show();
                mContext.startActivity(new Intent(mContext,BeanTable.class));
            }
        }
    }
}
