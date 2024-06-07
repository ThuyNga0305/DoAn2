package com.example.doan.cuahang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.doan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_1);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // set fragment home đầu tiên
        if (savedInstanceState == null) {
            HomeStoreFragment gt  = new HomeStoreFragment();
            FragmentManager mn = getSupportFragmentManager();
            mn.beginTransaction()
                    .add(R.id.fragment_1, gt)
                    .commit();
        }

    }
    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        if (item.getItemId() == R.id.Home ){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new HomeStoreFragment()).commit();
            return true;
        }else if (item.getItemId() == R.id.Hoat_Dong ){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new StoreActivitiesFragment()).commit();
            return true;
        }else if (item.getItemId() == R.id.Mon_An ){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new FoodOfStoreFragment()).commit();
            return true;
        }else if (item.getItemId() == R.id.Tai_Khoan ){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new StoreSettingsFragment()).commit();
            return true;
        }
        return false;
    };

}