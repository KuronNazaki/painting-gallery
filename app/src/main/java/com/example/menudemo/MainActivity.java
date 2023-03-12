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

    private AppDatabase database;
    List<PaintingEntity> list;
    PaintingRecyclerAdapter adapter;
    int recyclerViewItemPosition;
    Long deletedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDatabase(MainActivity.this);

        PaintingDao paintingDao = database.getPaintingDao();
        list = paintingDao.getPaintings();
        ContextMenuRecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new PaintingRecyclerAdapter(list, item -> {
            Intent detailIntent = new Intent(MainActivity.this, ItemDetailActivity.class);

            detailIntent.putExtra("PAINTING_ID", item.getId());
            startActivity(detailIntent);
        }, id -> {
            deletedId = id;
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
                Intent editIntent = new Intent(MainActivity.this, EditActivity.class);
                editIntent.putExtra("EDIT_PAINTING", deletedId);
                startActivity(editIntent);
                return true;
            case R.id.delete:
                PaintingDao dao = database.getPaintingDao();
                PaintingEntity painting = dao.getPaintingById(deletedId);
                dao.delete(painting);
                adapter.removeAt(deletedId);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.add:
                Intent addIntent = new Intent(MainActivity.this, AddNewActivity.class);
                startActivity(addIntent);
                return true;
            case R.id.cart:
                Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(cartIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}