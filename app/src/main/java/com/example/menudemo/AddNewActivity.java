package com.example.menudemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddNewActivity extends AppCompatActivity {
    private AppDatabase database;
    private Integer[] IMAGE_LIST = new Integer[] {R.mipmap.delacroix, R.mipmap.impression_sunrise, R.mipmap.starry_night, R.mipmap.the_magpie, R.mipmap.tsunami, R.mipmap.woman_with_parasol};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        database = AppDatabase.getDatabase(AddNewActivity.this);
        PaintingDao paintingDao = database.getPaintingDao();
        List<PaintingEntity> list = paintingDao.getPaintings();

        ((Button) findViewById(R.id.addButton)).setOnClickListener((l) -> {
            try {
                String name = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
                String artist = ((EditText) findViewById(R.id.artistEditText)).getText().toString();
                String dateOfPublish = ((EditText) findViewById(R.id.dateOfPublishEditText)).getText().toString();
                int drawableId = Integer.parseInt(((EditText) findViewById(R.id.drawableEditText)).getText().toString());

                if (name.isEmpty() || artist.isEmpty() || dateOfPublish.isEmpty()) {
                    Toast.makeText(AddNewActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (drawableId < 0 || drawableId > 5) {
                    Toast.makeText(AddNewActivity.this, "Image Id is between 0 and 5", Toast.LENGTH_SHORT).show();
                } else {
                    paintingDao.insert(new PaintingEntity(list.size() + 1L, name, artist, dateOfPublish, IMAGE_LIST[drawableId]));

                    Intent homeIntent = new Intent(AddNewActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(AddNewActivity.this, "Image Id is empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}