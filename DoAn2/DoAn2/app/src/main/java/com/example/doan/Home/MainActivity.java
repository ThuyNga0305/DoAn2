package com.example.doan.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.doan.R;
import com.example.doan.database.ListStoreFragment;
import com.example.doan.feature.setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolbar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // set fragment home đầu tiên
        if (savedInstanceState == null) {
            HomeFragment gt  = new HomeFragment();
            FragmentManager mn = getSupportFragmentManager();
            mn.beginTransaction()
                    .add(R.id.frame_layout, gt)
                    .commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            if (item.getItemId() ==  R.id.action_one) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                return true;
            }else if (item.getItemId() ==  R.id.action_two) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ListStoreFragment()).commit();
                return true;
            }else if (item.getItemId() ==  R.id.action_three) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new OrderFragment()).commit();
                return true;
            }else if (item.getItemId() ==  R.id.action_giohang) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CartFragment()).commit();
                return true;
            }else if (item.getItemId() ==  R.id.action_bon) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new SettingFragment()).commit();
                return true;
            }
            return false;
        }
    };
}