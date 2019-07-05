package com.sundram.shardhafertilizer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    View v;
    Button editProfile;
    private ProfileFragment context=this;
    //Textview to show currently logged in user
    TextView textViewId, textViewUsername, textViewPhone, textViewGender, textViewAddress, textViewPassword,name, textViewMobile;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        v =  inflater.inflate(R.layout.profile_fragment, null);
        textViewUsername = v.findViewById(R.id.name);
        //textViewGender = v.findViewById(R.id.education);
        textViewAddress = v.findViewById(R.id.occupation);
        textViewPhone = v.findViewById(R.id.mobileNumber);
        textViewGender = v.findViewById(R.id.gender);
        textViewPassword=v.findViewById(R.id.marriage);
        textViewMobile = v.findViewById(R.id.location);
        name = v.findViewById(R.id.education);

        User user = Config.getInstance(getContext()).getUser();
        textViewUsername.setText(user.getName());
        textViewGender.setText(user.getGender());
        textViewAddress.setText(user.getAddress());
        textViewPhone.setText(user.getMobileNumber());
        textViewPassword.setText(user.getPassword());
        textViewMobile.setText(user.getMobileNumber());
        name.setText(user.getName());

       /* editProfile = v.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"hey",Toast.LENGTH_SHORT).show();

            }
        });*/
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
