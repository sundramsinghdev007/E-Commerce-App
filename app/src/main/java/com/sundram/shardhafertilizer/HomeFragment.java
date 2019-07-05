package com.sundram.shardhafertilizer;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;




public class HomeFragment extends Fragment implements View.OnClickListener {

    private View thisFragment;
    private ViewFlipper viewFlipper;
    LinearLayout water, bio, organic, micro;
    TextView textView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        thisFragment = LayoutInflater.from(getActivity()).inflate(R.layout.activity_home_fragment, null);
        int images[] = {R.drawable.s1, R.drawable.s2, R.drawable.s4, R.drawable.s5, R.drawable.s3};
        viewFlipper = (ViewFlipper) thisFragment.findViewById(R.id.v_flipper);

        organic = thisFragment.findViewById(R.id.organic);
        bio = thisFragment.findViewById(R.id.bio);
        water = thisFragment.findViewById(R.id.water);
        micro = thisFragment.findViewById(R.id.micro);


        micro.setOnClickListener(this);
        bio.setOnClickListener(this);
        organic.setOnClickListener(this);
        water.setOnClickListener(this);




        for (int image : images) {
            flipperImages(image);
        }
        return thisFragment;

    }

    public void flipperImages(int image) {

        ImageView imageView = new ImageView(thisFragment.getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(thisFragment.getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(thisFragment.getContext(), android.R.anim.slide_out_right);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.micro:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View alertDialogView = inflater.inflate(R.layout.micronutrients,null);
                alertDialog.setView(alertDialogView);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
                break;
            case R.id.bio:

                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                View alertDialogView1 = inflater1.inflate(R.layout.biofertilizers,null);
                alertDialog1.setView(alertDialogView1);
                alertDialog1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog1.show();

                break;
            case R.id.organic:
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater2 = getActivity().getLayoutInflater();
                View alertDialogView2 = inflater2.inflate(R.layout.oragnic_manure,null);
                alertDialog2.setView(alertDialogView2);
                alertDialog2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog2.show();

                break;
            case R.id.water:
                AlertDialog.Builder alertDialog3= new AlertDialog.Builder(getActivity());
                LayoutInflater inflater3 = getActivity().getLayoutInflater();
                View alertDialogView3 = inflater3.inflate(R.layout.water_soluble,null);
                alertDialog3.setView(alertDialogView3);
                alertDialog3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog3.show();
                break;
        }

    }

}


