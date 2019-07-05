package com.sundram.shardhafertilizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListFragment extends Fragment {
    View v;
    Button btnProducDesc;
    String anmolratan;
    Resources res;
    private RecyclerView recyclerView;
    private List<ProductName> listdata;
    Intent intent;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    TextView textViewProductIds;

    public ProductListFragment() {

    }

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        v = inflater.inflate(R.layout.product_list_fragment, null);
        //btnProducDesc = v.findViewById(R.id.productDescription);
        //btnProducDesc = v.findViewById(R.id.rateList);
        textViewProductIds = v.findViewById(R.id.productId);

        recyclerView = v.findViewById(R.id.productRecycler);
        recyclerView.setHasFixedSize(true);
        //RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),listdata);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setAdapter(recyclerViewAdapter);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listdata = new ArrayList<>();

        //Toast.makeText(getContext(),"shardepreferecneId: "+value,Toast.LENGTH_SHORT);
        requestQueue = Volley.newRequestQueue(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setMessage("Loading Data Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.product_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //Log.i("List Response", String.valueOf(array));
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject products = array.getJSONObject(i);

                                //adding the product to product list
                                listdata.add(new ProductName(
                                        products.getInt("id"),
                                        products.getString("title"),
                                        products.getString("shortdesc"),
                                        products.getString("quantity"),
                                        products.getString("Packing"),
                                        products.getString("MRP"),
                                        products.getString("image")
                                ));
                                }
                            RecyclerViewAdapter productAdapter = new RecyclerViewAdapter(getContext(), listdata);
                            recyclerView.setAdapter(productAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getContext(),
                            "Oops. Timeout error!" + error,
                            Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

}
