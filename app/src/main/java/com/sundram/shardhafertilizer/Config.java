package com.sundram.shardhafertilizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class Config {
    //URL to our login.php file
    public static final String server_url = "https://eeragongoon001.000webhostapp.com/login.php";
    public static final String product_url = "https://eeragongoon001.000webhostapp.com/Api.php";
    public static final String product_Data_To_Description_URL = "https://eeragongoon001.000webhostapp.com/api.php";
    public static final String addToCart_url = "https://eeragongoon001.000webhostapp.com/addToCart.php";
    public static final String getRateList_Url = "https://eeragongoon001.000webhostapp.com/getRateListData.php";


    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "name";
    public static final String KEY_Mobile = "mobilenumber";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ADDRESS="address";

    //keys for addToCart response

    public static final String Key_User_Id = "userId";
    public static final String KEY_PRODUCT_ID ="productId";
    public static final String KEY_PRODUCT_NAME = "productName";
    public static final String KEY_PRODUCT_PACKING = "packing";
    public static final String KEY_PRODUCT_RATEMT = "RateMt";
    public static final String KEY_PRODUCT_MRP = "MRP";
    public static final String KEY_PRODUCT_REM = "REM";
    public static final String KEY_PRODUCT_CONTENT = "productContent";
    public static final String KEY_PRODUCT_QUANTITY = "quantity";
    public static final String KEY_PRODUCT_PRICE = "totalPrice";
    public static final String KEY_PRODUCT_STATUS = "status";

    //Key for product data
    public static final String KEY_PRDUCT_SHERED = "product";
    public static final String KEY_PRODUCT_IMAGE = "productImage";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "mylogin";
    public static final String SHARED_PREF_NAME_REGISTER = "myRegister";
    public static final String SARED_PREF_ADD_TO_CART = "addToCart";

    //This would be used to store the email of current logged in user
    public static final String Mobile_SHARED_PREF = "mobileNumber";

    public static final String USRE_NAME_SHARED_PREF = "Name";
    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    private static Config mInstance;
    private static Context mCtx;

    private Config(Context context) {
        mCtx = context;
    }

    public static synchronized Config getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Config(context);
        }
        return mInstance;
    }
    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_Mobile, user.getMobileNumber());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_ADDRESS,user.getAddress());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.apply();
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID,-1),
                sharedPreferences.getString(KEY_USERNAME,null),
                sharedPreferences.getString(KEY_Mobile, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_ADDRESS,null),
                sharedPreferences.getString(KEY_PASSWORD, null)

        );
    }

    public void userRegister(User user) {
        SharedPreferences regishSharepreference = mCtx.getSharedPreferences(SHARED_PREF_NAME_REGISTER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = regishSharepreference.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_Mobile, user.getMobileNumber());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_ADDRESS,user.getAddress());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.apply();
    }
    public User getRegisterUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_REGISTER, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID,-1),
                sharedPreferences.getString(KEY_USERNAME,null),
                sharedPreferences.getString(KEY_Mobile, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_ADDRESS,null),
                sharedPreferences.getString(KEY_PASSWORD, null)

        );
    }

    //add to cart data
    public void addToCart(addToCartCunstructor addToCart){
        SharedPreferences addTOCartSharedPreference = mCtx.getSharedPreferences(SARED_PREF_ADD_TO_CART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = addTOCartSharedPreference.edit();
        editor.putString(KEY_PRODUCT_NAME,addToCart.getProductName());
        editor.putString(KEY_PRODUCT_CONTENT,addToCart.getDescription());
        editor.putString(KEY_PRODUCT_QUANTITY,addToCart.getQuantity());
        editor.putString(KEY_PRODUCT_PRICE,addToCart.getPrice());
        editor.putString(KEY_PRODUCT_STATUS,addToCart.getStatus());
        editor.putString(Key_User_Id,addToCart.getUserid());
        editor.apply();
    }

    public addToCartCunstructor getCartData(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SARED_PREF_ADD_TO_CART,Context.MODE_PRIVATE);
        return new addToCartCunstructor(
                sharedPreferences.getString(KEY_PRODUCT_NAME,null),
                sharedPreferences.getString(KEY_PRODUCT_CONTENT,null),
                sharedPreferences.getString(KEY_PRODUCT_QUANTITY,null),
                sharedPreferences.getString(KEY_PRODUCT_PRICE,null),
                sharedPreferences.getString(KEY_PRODUCT_STATUS,null),
                sharedPreferences.getString(Key_User_Id,null)
        );
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }
    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, HomeActivity.class));

    }
}
