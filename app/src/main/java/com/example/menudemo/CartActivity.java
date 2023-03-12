package com.example.menudemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartActivity extends AppCompatActivity {
    public static final String CART_LIST = "CART_LIST";

    private ArrayList<PaintingEntity> cartList = new ArrayList<>();
    private Set<String> cartIdSet;
    private Set<PaintingEntity> cartEntitySet = new HashSet<>();
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = AppDatabase.getDatabase(CartActivity.this);
        PaintingDao paintingDao = database.getPaintingDao();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        cartIdSet = preferences.getStringSet("CART_LIST", new HashSet<>());

        cartIdSet.forEach((id) -> {
            cartEntitySet.add(paintingDao.getPaintingById(Long.parseLong(id)));
        });

        RecyclerView cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartList.addAll(cartEntitySet);
        CartRecyclerAdapter adapter = new CartRecyclerAdapter(cartList);
        cartRecyclerView.setAdapter(adapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.back_button).setOnClickListener(l -> {
            Intent mainIntent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(mainIntent);
        });
    }
}