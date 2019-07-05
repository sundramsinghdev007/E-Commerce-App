package com.sundram.shardhafertilizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDescription extends AppCompatActivity {

    TextView textViewName, textViewContent,getTheRateList, textViewPrice, textViewProductCount, textView3, textViewTotalPrice, textViewProductId;
    ImageView imageViewProductImage;
    String name, content, price, quantity;
    double totalPrice;
    EditText editTextPrice, productQuantityEditText;

    StringRequest stringRequest;
    RequestQueue requestQueue;
    int numberOfProduct;
    String Id, intPrice;
    AppCompatSpinner productQuantitySpinner;
    private List<ProductName> mData;
    private Context context = this;

    Double Price = 0.0;
    AppCompatImageButton buttonAdd, buttonRemove, addToCartButton;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        addToCartButton = findViewById(R.id.addToCart);
        textViewProductId = (TextView) findViewById(R.id.productId);

        getTheRateList = findViewById(R.id.getTheRateList);

        //edittext values
        productQuantityEditText = (EditText) findViewById(R.id.productQuantityEditText);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);

        price = String.valueOf(editTextPrice.getText()).trim();

        quantity = String.valueOf(productQuantityEditText.getText()).trim();

        Log.i("price or quantity",price+quantity);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewContent = findViewById(R.id.textViewContent);
        //textViewPrice = findViewById(R.id.textViewPrice);
        imageViewProductImage = findViewById(R.id.imageViewProduct);
        requestQueue = Volley.newRequestQueue(this);

        name = getIntent().getStringExtra("name");
        content = getIntent().getStringExtra("content");
        Id = getIntent().getStringExtra("id");
        price = String.valueOf(editTextPrice.getText()).trim();
        quantity = String.valueOf(productQuantityEditText.getText()).trim();
        final String proId = getIntent().getStringExtra("id");
        //Price = Double.valueOf((String.valueOf(price)));

        textViewProductId.setText(Id);
        textViewProductId.setVisibility(View.GONE);

        textViewName.setText(name);
        textViewContent.setText(content);
        //textViewPrice.setText(price);
        //textViewid.setText(Id);

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = extras.getParcelable("imageResource");
        imageViewProductImage.setImageBitmap(bmp);

        //AddTOCart
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = Config.getInstance(getApplicationContext()).getUser();

                final int userid = user.getId();
                price = String.valueOf(editTextPrice.getText()).trim();

                quantity = String.valueOf(productQuantityEditText.getText()).trim();

                Log.i("price or quantity",price+quantity);
                Id = getIntent().getStringExtra("id").trim();
                final String userId = String.valueOf(userid).trim();
                name = getIntent().getStringExtra("name").trim();
                content = getIntent().getStringExtra("content").trim();
                //price = getIntent().getStringExtra("price").trim();

                stringRequest = new StringRequest(Request.Method.POST, Config.addToCart_url,
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onResponse(String response) {
                                // Hiding the progress dialog after all task complete.
                                try {

                                    JSONObject object = new JSONObject(response);

                                    if (!object.getBoolean("error")) {
                                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                                        JSONObject getObject = object.getJSONObject("user");


                                        //Log.i("jsonObject Response", String.valueOf(getObject));

                                        addToCartCunstructor addToCartProduct = new addToCartCunstructor(
                                                getObject.getString("productName"),
                                                getObject.getString("productContent"),
                                                getObject.getString("quantity"),
                                                getObject.getString("totalPrice"),
                                                getObject.getString("userId"),
                                                getObject.getString("status")
                                        );

                                        //Log.i("productName Reasponse", String.valueOf(addToCartProduct));
                                        //storing the user data in sharedprefernce
                                        Config.getInstance(ProductDescription.this).addToCart(addToCartProduct);
                                        //int drawable = getApplicationContext().getResources().getColor(R.color.materialblue);
                                        //0addToCartButton.setBackgroundColor(drawable);
                                        //Toast.makeText(ProductDescription.this,"error"+response,Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ProductDescription.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Hiding the progress dialog after all task complete.

                        if (error instanceof NetworkError) {
                        } else if (error instanceof ServerError) {
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(ProductDescription.this,
                                    "Oops. Timeout error!" + error,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("productId", Id);
                        params.put("userId", userId);
                        params.put("productName", name);
                        params.put("productContent", content);
                        params.put("quantity", quantity);
                        params.put("totalPrice", price);
                        return params;
                    }
                };


                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);

            }

        });

        getTheRateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDescription.this,BeanTable.class));
            }
        });
    }

}
