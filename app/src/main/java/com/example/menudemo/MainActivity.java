package com.example.menudemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String PAINTING_ADDED_TO_CART = "PAINTING_ADDED_TO_CART";

    List<Painting> list = new ArrayList<>();
    static final List<Painting> listToBeSentToCart = new ArrayList<>();
    PaintingRecyclerAdapter adapter;
    int recyclerViewItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Painting paintingAddedToCart = getIntent().getSerializableExtra(PAINTING_ADDED_TO_CART, Painting.class);
        if (paintingAddedToCart != null) {
            listToBeSentToCart.add(paintingAddedToCart);
        }

        list = getListOfPainting();
        ContextMenuRecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new PaintingRecyclerAdapter(list, item -> {
            Intent detailIntent = new Intent(MainActivity.this, ItemDetailActivity.class);
            detailIntent.putExtra("PAINTING", item);
            startActivity(detailIntent);
        }, position -> {
            recyclerViewItemPosition = position;
            return false;
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.display_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                return true;
            case R.id.share:
                return false;
            case R.id.delete:
                adapter.removeAt(recyclerViewItemPosition);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.search:
                return false;
            case R.id.cart:
                Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                cartIntent.putExtra(CartActivity.CART_LIST, (ArrayList<Painting>) listToBeSentToCart);
                startActivity(cartIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Painting> getListOfPainting() {
        List<Painting> list = new ArrayList<>();
        list.add(new Painting("The Starry Night", "Vicent van Gogh", "June 1889", R.mipmap.starry_night));
        list.add(new Painting("Impression, Sunrise", "Claude Monet", "1872", R.mipmap.impression_sunrise));
        list.add(new Painting("The Magpie", "Claude Monet", "1869", R.mipmap.the_magpie));
        list.add(new Painting("Woman with a Parasol – Madame Monet and Her Son", "Claude Monet", "1975", R.mipmap.woman_with_parasol));
        list.add(new Painting("The Great Wave off Kanagawa", "Hokusai", "1831", R.mipmap.tsunami));
        list.add(new Painting("Liberty Leading the People", "Eugène Delacroix", "1830", R.mipmap.delacroix));
        return list;
    }
}