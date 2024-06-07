package com.example.doan.feature.setting;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.doan.R;
import com.example.doan.cuahang.login.Login_Activity;
import com.example.doan.model.Customer;
import com.example.doan.profile.ChangeProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;


public class SettingFragment extends Fragment {
    TextView txtDoiThongTin, txtChangePassWord;
    ImageView logout;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference1 = firebaseDatabase.getReference("KhachHang");
    TextView tvNameProfile, tvMailProfile, tvPhoneProfile, tvAddressProfile, tvNgaySinh;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    public static String userID;
    public static String email;
    public static String pass;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        tvNameProfile = view.findViewById(R.id.tvNameProfile);
        tvMailProfile = view.findViewById(R.id.tvMailProfile);
        tvPhoneProfile = view.findViewById(R.id.tvPhoneProfile);
        tvAddressProfile = view.findViewById(R.id.tvDiaChiProfile);
        tvNgaySinh = view.findViewById(R.id.tvNgaySinhProfile);
        txtDoiThongTin = view.findViewById(R.id.txtDoiThongTin);
        txtChangePassWord = view.findViewById(R.id.txtDoiMatKhau);
        logout = view.findViewById(R.id.ivLogout);

        FirebaseUser mFirebaseUser = fAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            userID = mFirebaseUser.getUid();
            databaseReference1.child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Customer nguoiDung = dataSnapshot.getValue(Customer.class);
                    if(nguoiDung != null) {
                        tvNameProfile.setText(nguoiDung.getUserName());
                        tvPhoneProfile.setText(nguoiDung.getUserSDT());
                        tvAddressProfile.setText(nguoiDung.getUserDiaChi());
                        tvNgaySinh.setText(nguoiDung.getUserNgaySinh());
                        pass = nguoiDung.getUserPass();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            email = mFirebaseUser.getEmail();
            tvMailProfile.setText(email);

        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view1 = layoutInflater.inflate(R.layout.logout_alert_dialog, null);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(), Login_Activity.class));
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view1);
                builder.show();
            }
        });

        txtDoiThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ChangeProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        txtChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ChangePassFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }

}