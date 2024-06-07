package com.example.doan.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.doan.R;
import com.example.doan.adapter.FoodAdapter;
import com.example.doan.cuahang.HomeStoreFragment;
import com.example.doan.model.Food;
import com.example.doan.model.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFoodFragment extends Fragment {

    RecyclerView rcvStoreSuggest;
    RelativeLayout btnBack;

    FoodAdapter foodAdapter;
    ArrayList<Food> list = new ArrayList<>();

    ArrayList<Store> storeList = new ArrayList<>();

    public SearchFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvStoreSuggest = view.findViewById(R.id.rcvSearchFood);
        btnBack = view.findViewById(R.id.btn_back_search);

        foodAdapter = new FoodAdapter(list, getContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcvStoreSuggest.setLayoutManager(layoutManager);
        rcvStoreSuggest.setAdapter(foodAdapter);

        Bundle bundle = this.getArguments();
        String search = bundle.getString("search");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MonAn");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Food food =dataSnapshot1.getValue(Food.class);
                    if(food.getNameMonAn().contains(search)){
                        list.add(food);
                    }

                }
                foodAdapter.setData(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("SearchFragment", "cancel", databaseError.toException());
            }
        });

        btnBack.setOnClickListener(view1 -> {
            FragmentTransaction transaction =  getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_1,new HomeStoreFragment());
            transaction.commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_food, container, false);
    }
}