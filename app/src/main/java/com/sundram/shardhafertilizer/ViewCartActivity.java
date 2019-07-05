package com.sundram.shardhafertilizer;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ViewCartActivity extends AppCompatActivity {
    private List<addToCartCunstructor> listdata;
    public static final String cart_Url = "https://eeragongoon001.000webhostapp.com/viewCart.php";
    public static final String remove_cart_product_url = "https://eeragongoon001.000webhostapp.com/removeFramCart.php";
    ImageView removeProductFromCart;
    RecyclerView recyclerView;
    CustomCartAdapter customCartAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        recyclerView = findViewById(R.id.viewCartRecyclerView);

        progressDialog = new ProgressDialog(ViewCartActivity.this);

        listdata = new ArrayList<>();
        User userData = Config.getInstance(ViewCartActivity.this).getUser();
        final String userId = String.valueOf(userData.getId()).trim();

        progressDialog.show();
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, cart_Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {

                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //Log.i("View Cart Response", String.valueOf(array));
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array

                                JSONObject cartData = array.getJSONObject(i);

                                listdata.add(new addToCartCunstructor(
                                        cartData.getString("cartProductName"),
                                        cartData.getString("cartProductContent"),
                                        cartData.getString("cartProductQuantity"),
                                        cartData.getString("cartProductTotalPrice"),
                                        cartData.getString("userId"),
                                        cartData.getString("status")
                                ));

                            }
                            if (listdata.isEmpty()){
                                Toast.makeText(ViewCartActivity.this,"Cart is empty Or Connection Problem!!",Toast.LENGTH_SHORT).show();
                            }else {
                                customCartAdapter = new CustomCartAdapter(ViewCartActivity.this, listdata);
                                recyclerView.setAdapter(customCartAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ViewCartActivity.this));
                                customCartAdapter.notifyDataSetChanged();
                            }

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
                    Toast.makeText(ViewCartActivity.this,
                            "Oops. Timeout error!" + error,
                            Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                return params;
            }
        };
        Volley.newRequestQueue(ViewCartActivity.this).add(stringRequest);


    }

    public void removeProductFromCart(View view) {

        addToCartCunstructor productData = Config.getInstance(ViewCartActivity.this).getCartData();

        final String productname = productData.getProductName().trim();
        Log.i("Product name",productname);
        User userData = Config.getInstance(ViewCartActivity.this).getUser();
        final String userId = String.valueOf(userData.getId()).trim();
        progressDialog.show();
        progressDialog.setMessage("Wait ...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, remove_cart_product_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            //for refreshing activity itself
                            finish();
                            getApplicationContext().startActivity(getIntent());
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
                    Toast.makeText(ViewCartActivity.this,
                            "Oops. Timeout error!" + error.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                params.put("productname",productname);
                return params;
            }
        };
        Volley.newRequestQueue(ViewCartActivity.this).add(stringRequest);
    }

}
