package com.sundram.shardhafertilizer;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //private AwesomeValidation awesomeValidation;
    String forget_url = "https://eeragongoon001.000webhostapp.com/forgetPassword.php";
    EditText mobileNumber, password, phone, newPass;
    TextView textViewForgetPass;
    private Button button;
    String mobile, pass, forgetMobile;
    private Button btnlogin;
    private String mobilenumber, newpassword;
    Boolean checkEditText;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    TextView textViewlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mobileNumber = findViewById(R.id.editTextmobile);
        password = findViewById(R.id.editTextPass);
        btnlogin = findViewById(R.id.btn_Login);
        textViewForgetPass = findViewById(R.id.textViewForgetPass);
        textViewlogin = findViewById(R.id.textViewlogin);

        //getting oldpass
        //User user = Config.getInstance(LoginActivity.this).getRegisterUser();
        //forgetMobile = user.getMobileNumber().trim();

        //textViewForgetPass.setText(user.getPassword());
        //String oldpass = textViewForgetPass.getText().toString().trim();
        //textViewlogin.setText(old);
        progressDialog = new ProgressDialog(LoginActivity.this);
        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        //onClick on forgetPassword
        textViewForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });

        //onClick for login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnection()) {
                    checkEditText();
                    if (!checkEditText) {
                        login();
                    } else {
                        Snackbar.make(v, "Please fill the detail !", Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    Snackbar.make(v, "Connection Problem", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login() {

        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        final String mobile = mobileNumber.getText().toString().trim();
        final String pass = password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.server_url,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);

                            //Log.i("tagconvertstr", "["+response+"]");
                           //Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    JSONObject userJson = obj.getJSONObject("user");
                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("name"),
                                        userJson.getString("mobilenumber"),
                                        userJson.getString("gender"),
                                        userJson.getString("address"),
                                        userJson.getString("password")

                                );
                                //storing the user in shared preferences
                                Config.getInstance(getApplicationContext()).userLogin(user);
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Hiding the progress dialog after all task complete.
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(LoginActivity.this,
                            "Oops. Timeout error!" + error,
                            Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getApplicationContext(),"Something Went Wrong !",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobileNumber", mobile);
                params.put("password", pass);
                return params;
            }
        };


        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    //forgetPassword

    public void forgetPassword() {

        TextView heading;

        //Custom Dialog
        final Dialog dialog = new Dialog(LoginActivity.this, R.style.AppThemeDialog);
        dialog.setContentView(R.layout.custom_forget_password);
        //dialog.setTitle(R.string.forget_password);
        button = dialog.findViewById(R.id.btnForgetPass);
        phone = dialog.findViewById(R.id.editTextOldPass);
        newPass = dialog.findViewById(R.id.editTextNewPass);
        heading = dialog.findViewById(R.id.textForgetPass);
        //heading.setText(user.getPassword());

        //heading.setText(forgetMobile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnection()) {
                    checkForgetPasswordEditText();
                    if (!checkEditText) {
                        //code start
                        progressDialog.setMessage("Please Wait.....");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);

                        mobilenumber = phone.getText().toString().trim();
                        newpassword = newPass.getText().toString().trim();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, forget_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        //Toast.makeText(getApplication(),response,Toast.LENGTH_LONG).show();
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if(!jsonObject.getBoolean("error")){
                                                dialog.dismiss();
                                                Toast.makeText(getApplication(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplication(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    //error.toString()
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.dismiss();
                                        //Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_LONG).show();
                                        if (error instanceof NetworkError) {
                                        } else if (error instanceof ServerError) {
                                        } else if (error instanceof AuthFailureError) {
                                        } else if (error instanceof ParseError) {
                                        } else if (error instanceof NoConnectionError) {
                                        } else if (error instanceof TimeoutError) {
                                            Toast.makeText(LoginActivity.this,
                                                    "Oops. Timeout error!" + error,
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("mobilenumber",mobilenumber);
                                params.put("newpassword",newpassword);
                                return params;
                            }
                        };
                 // Adding the StringRequest object into requestQueue.
                        requestQueue.add(stringRequest);
                        //code end

                    } else {
                        Snackbar.make(v, "Please fill the detail !", Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    Snackbar.make(v, "Connection Problem", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

//isEmpty login field
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void checkEditText() {

        mobile = mobileNumber.getText().toString().trim();
        pass = password.getText().toString().trim();

        if (mobile.isEmpty() || pass.isEmpty()) {

            checkEditText = true;
        } else {
            checkEditText = false;
        }
    }
//isEmpty forgetpassword field
    public void checkForgetPasswordEditText() {
        mobilenumber = phone.getText().toString().trim();
        newpassword = newPass.getText().toString().trim();

        if (mobilenumber.isEmpty()) {

            checkEditText=true;
            if (newpassword.isEmpty() || newpassword.length() < 8) {
                checkEditText = true;

            }
        }else {
            checkEditText = false;
        }
    }


    public boolean isInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() &&
                activeNetworkInfo.isConnected();
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
