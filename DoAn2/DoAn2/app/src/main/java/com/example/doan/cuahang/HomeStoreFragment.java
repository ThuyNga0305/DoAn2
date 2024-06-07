package com.example.doan.cuahang;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.doan.Home.SearchFoodFragment;
import com.example.doan.R;
import com.example.doan.adapter.ShowFoodAdapter;
import com.example.doan.database.FoodDAO;
import com.example.doan.model.CartDetails;
import com.example.doan.model.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeStoreFragment extends Fragment {

    RecyclerView rcvMenu;
    TextView tvShowMenu;
    TextView tvDoanhThu;
    public static ShowFoodAdapter foodAdapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FoodDAO foodDAO = new FoodDAO(getActivity());
    ArrayList<Food> list = new ArrayList<>();
    ArrayList<CartDetails> listCart = new ArrayList<>();

    EditText edtSearchText;
    ImageView btnSearch;
    public HomeStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_store, container, false);

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvShowMenu = view.findViewById(R.id.tv_show_menu);
        rcvMenu = view.findViewById(R.id.rcvMenu);
        tvDoanhThu=view.findViewById(R.id.tv_Doanhthu);
        btnSearch = view.findViewById(R.id.btn_search);
        String id = mAuth.getCurrentUser().getUid();
        edtSearchText = view.findViewById(R.id.search_text);

        LinearLayoutManager menu = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvMenu.setLayoutManager(menu);
        list = foodDAO.getAllMenu(id);
        getDonByCuaHangID();
        foodAdapter = new ShowFoodAdapter(list, getActivity());
        rcvMenu.setAdapter(foodAdapter);

        btnSearch.setOnClickListener(view13 -> {
            Bundle bundle = new Bundle();
            bundle.putString("search",edtSearchText.getText().toString().trim());
            SearchFoodFragment searchFragment = new SearchFoodFragment();
            searchFragment.setArguments(bundle);
            FragmentTransaction transaction =  getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_1,searchFragment);
            transaction.commit();
        });

        tvShowMenu.setOnClickListener(view1 -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_1, new FoodOfStoreFragment());
            transaction.commit();
        });
    }
    public void getDonByCuaHangID() {
        String i = mAuth.getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Đơn hàng");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot data : ds.getChildren()) {
                        CartDetails cartDetails = data.getValue(CartDetails.class);
                        if (cartDetails != null) {
                            if (cartDetails.getCuaHangID().equals(i)) {
                                listCart.add(cartDetails);
                                Log.d("DonHang", "ListDH" + list);
                                final DecimalFormat formatter = new DecimalFormat("###,###,###");
                                int sum=0;
                                for (CartDetails element : listCart) {
                                    sum +=element.getGia()*element.getSoluong();
                                    tvDoanhThu.setText(formatter.format(sum)+ " VNĐ");
                                }
                            }
                        }


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}