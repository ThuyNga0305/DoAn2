package com.example.doan.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.doan.R;
import com.example.doan.cuahang.login.Login_Activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_2);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // set fragment home đầu tiên
        if (savedInstanceState == null) {
            StoreFragment gt  = new StoreFragment();
            FragmentManager mn = getSupportFragmentManager();
            mn.beginTransaction()
                    .add(R.id.fragment_2, gt)
                    .commit();
        }

    }
    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        if (item.getItemId() == R.id.qlycuahang){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_2, new StoreFragment()).commit();
            return true;
        }else if (item.getItemId() == R.id.qlynguoidung){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_2, new CustomerFragment()).commit();
            return true;
        }else if (item.getItemId() == R.id.theloaimonan){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_2, new CategoriesFragment()).commit();
            return true;
        }else if (item.getItemId() == R.id.dangxuat){
            startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            return true;
        }
        return false;
    };
}