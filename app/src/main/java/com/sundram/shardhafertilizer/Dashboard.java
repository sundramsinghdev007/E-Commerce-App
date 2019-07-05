package com.sundram.shardhafertilizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
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

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    String mobile = "05876 233100";
    NavigationView navigationView;
    TextView textViewdrawerusername, textViewgender;
    String name, gender;
    RecyclerView cartRecylerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sharda Fertilizer");
        setSupportActionBar(toolbar);

        //viewCartActivity
        progressDialog = new ProgressDialog(Dashboard.this);
        drawer = findViewById(R.id.dashboard_layout);

        navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        textViewdrawerusername = headerView.findViewById(R.id.textViewDrawerUserName);
        User user = Config.getInstance(Dashboard.this).getUser();
        textViewdrawerusername.setText(user.getName());

        navigationView.setNavigationItemSelectedListener(this);

        //enable hamburger
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //s1 = getResources().getStringArray(R.array.jobType);
        //loading the default fragment
        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private MenuItem menuItem;


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    //home
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_products:
                        fragment = new ProductListFragment();
                        break;
                    //viewCart
                    //case R.id.action_viewCart:
                    //fragment = new ViewFragment();
                    //break;

                }
                return loadFragment(fragment);
            }
        });

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.nav_AboutUs) {
            startActivity(new Intent(getApplicationContext(), AboutUs.class));
            //Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_ContactUs) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.logo);
            builder.setTitle("Contact to our service provider.");
            builder.setMessage("Do you want to make a call !");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent callintent = new Intent(Intent.ACTION_DIAL);
                    callintent.setData(Uri.parse("tel:" + mobile));
                    startActivity(callintent);
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            //builder.show();
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(getApplicationContext(), Gallery.class));
        } else if (id == R.id.nav_careers) {
            startActivity(new Intent(getApplicationContext(), Careers.class));
        } else if (id == R.id.nav_promote) {
            //making app to share with other app
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody = "Your Message Here !";
            String shareSub = "Your subject here !";
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
            myIntent.putExtra(Intent.EXTRA_TEXT, shareSub);
            startActivity(Intent.createChooser(myIntent, "Share Using"));
        } else if (id == R.id.nav_logOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setIcon(R.drawable.ic_add_alert_black);
            builder.setTitle("Attention !");
            builder.setMessage("Are you sure you want to Logout !")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Config.getInstance(getApplicationContext()).logout();

                            Toast.makeText(getApplicationContext(), "Logout Successfull", Toast.LENGTH_LONG).show();
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to exit !")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuViewCart) {
            startActivity(new Intent(getApplicationContext(), ViewCartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
