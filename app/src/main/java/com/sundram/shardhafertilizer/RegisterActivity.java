package com.sundram.shardhafertilizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.TEXT_INPUT_LAYOUT;

public class RegisterActivity extends AppCompatActivity {

    private AwesomeValidation awesomeValidation;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    EditText Name, Mobileno, City, Pass;
    String server_url = "https://eeragongoon001.000webhostapp.com/registration.php";
    StringRequest stringRequest;
    Button Register;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Boolean CheckEditText;
    String name, mobileno, gender, address, password;
    public static final String my_pref = "heys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = findViewById(R.id.et_name);
        Mobileno = findViewById(R.id.et_mobile_no);
        radioSexGroup = findViewById(R.id.gender);
        City = findViewById(R.id.et_city);
        Pass = findViewById(R.id.et_pass);
        requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        Register = findViewById(R.id.btn_register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInternetConnection()) {
                    checkEditTextIsEmpty();
                    if (CheckEditText) {
                        if (awesomeValidation.validate()) {
                            registration();
                        }
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    public void registration() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        password = Pass.getText().toString().trim();

        stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                        try {
                            JSONObject object = new JSONObject(response);

                            if (!object.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                                JSONObject userJSON = object.getJSONObject("user");
                                Log.i("userJsonResponse", String.valueOf(userJSON));
                                //creating a new user object
                                User user = new User(
                                        userJSON.getInt("id"),
                                        userJSON.getString("name"),
                                        userJSON.getString("mobilenumber"),
                                        userJSON.getString("gender"),
                                        userJSON.getString("address"),
                                        userJSON.getString("password")

                                );

                                //storing the user data in sharedprefernce
                                Config.getInstance(RegisterActivity.this).userRegister(user);

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();

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
                progressDialog.dismiss();

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(RegisterActivity.this,
                            "Oops. Timeout error!" + error,
                            Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("mobilenumber", mobileno);
                params.put("gender", gender);
                params.put("address", address);
                params.put("password", password);
                return params;
            }
        };


        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    public void checkEditTextIsEmpty() {

        CheckEditText = true;
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        name = Name.getText().toString().trim();
        mobileno = Mobileno.getText().toString().trim();
        address = City.getText().toString().trim();
        password = Pass.getText().toString().trim();

        // get selected radio button from radioGroup
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioSexButton = (RadioButton) findViewById(selectedId);
        gender = radioSexButton.getText().toString().trim();
        // Checking whether EditText value is empty or not.
        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.et_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.et_mobile_no, RegexTemplate.NOT_EMPTY, R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.et_city, RegexTemplate.NOT_EMPTY, R.string.villageerror);
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this, R.id.et_pass, regexPassword, R.string.passerror);
        //awesomeValidation.addValidation(this, Mobileno, Patterns.EMAIL_ADDRESS, R.string.nameerror);
        // to validate the confirmation of another field
        //String regexPassword = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";

        //awesomeValidation.addValidation(this, R.id.editTextDob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.nameerror);
        //awesomeValidation.addValidation(this, R.id.editTextAge, Range.closed(13, 60), R.string.ageerror);


    }

    public boolean isInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
