package com.example.menudemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    public static final String CART_LIST = "CART_LIST";

    private ArrayList<Painting> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartList = (ArrayList<Painting>) getIntent().getSerializableExtra(CART_LIST, ArrayList.class);
        CartRecyclerAdapter adapter = new CartRecyclerAdapter(cartList);
        cartRecyclerView.setAdapter(adapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.back_button).setOnClickListener(l -> {
            Intent mainIntent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(mainIntent);
        });
    }
}