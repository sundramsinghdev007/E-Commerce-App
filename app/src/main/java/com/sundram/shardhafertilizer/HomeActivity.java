package com.sundram.shardhafertilizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;

public class HomeActivity extends AppCompatActivity {
    private boolean loggedIn = false;
    TextView textView;
    LinearLayout linearLayout0, linearLayout1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = findViewById(R.id.textConnectionCheck);
        linearLayout1 = findViewById(R.id.linear_layout0);
        linearLayout0 = findViewById(R.id.linear_layout1);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            changeTextStatus(true);
        } else {
            changeTextStatus(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
        if (Config.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, Dashboard.class));
            finish();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }
    // Method to change the text status
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void changeTextStatus(boolean isConnected) {

        // Change status according to boolean value
        if (isConnected) {
            linearLayout0.setVisibility(View.GONE);
            linearLayout1.setVisibility(View.VISIBLE);
            textView.setText("Internet Connected.");
            textView.setTextColor(Color.parseColor("#00ff00"));
        } else {
            linearLayout0.setVisibility(View.VISIBLE);
            linearLayout1.setVisibility(View.GONE);
            textView.setText("Internet Disconnected.");
            textView.setTextSize(16);
            textView.setAllCaps(true);
            textView.setPadding(10,10,10,0);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(Color.parseColor("#ff0000"));
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void login(View view)
    {
        //finish();
        Intent reg = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(reg);
        //finishAffinity();
    }
    public void register(View view){
        startActivity(new Intent(this,RegisterActivity.class));
        //finish();
    }

}
