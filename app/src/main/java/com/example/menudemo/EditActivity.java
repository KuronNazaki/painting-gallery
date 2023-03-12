package com.example.menudemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EditActivity extends AppCompatActivity {
    private AppDatabase database;
    private List<Integer> imageList = new ArrayList<>(Arrays.asList(R.mipmap.delacroix, R.mipmap.impression_sunrise, R.mipmap.starry_night, R.mipmap.the_magpie, R.mipmap.tsunami, R.mipmap.woman_with_parasol));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        database = AppDatabase.getDatabase(EditActivity.this);
        PaintingDao paintingDao = database.getPaintingDao();
        List<PaintingEntity> list = paintingDao.getPaintings();

        PaintingEntity currentPainting = paintingDao.getPaintingById(getIntent().getLongExtra("EDIT_PAINTING", 1));
        ((EditText) findViewById(R.id.editNameEditText)).setText(currentPainting.getName());
        ((EditText) findViewById(R.id.editArtistEditText)).setText(currentPainting.getArtist());
        ((EditText) findViewById(R.id.editDateOfPublishEditText)).setText(currentPainting.getDateOfPublish());
//        int index = imageList.indexOf(imageList.stream().filter((image) -> Long.valueOf(image).equals(currentPainting.getId())).collect(Collectors.toList()).get(0));
        int index = 0;
        for (int i = 0; i < imageList.size(); i++) {
            if (imageList.get(i).equals(currentPainting.getDrawableId())) {
                index = i;
                break;
            }
        }
        ((EditText) findViewById(R.id.editDrawableEditText)).setText(String.valueOf(index));

        ((Button) findViewById(R.id.editButton)).setOnClickListener((l) -> {
            try {
                String name = ((EditText) findViewById(R.id.editNameEditText)).getText().toString();
                String artist = ((EditText) findViewById(R.id.editArtistEditText)).getText().toString();
                String dateOfPublish = ((EditText) findViewById(R.id.editDateOfPublishEditText)).getText().toString();
                int drawableId = Integer.parseInt(((EditText) findViewById(R.id.editDrawableEditText)).getText().toString());

                currentPainting.setName(name);
                currentPainting.setArtist(artist);
                currentPainting.setDateOfPublish(dateOfPublish);
                currentPainting.setDrawableId(imageList.get(drawableId));

                if (name.isEmpty() || artist.isEmpty() || dateOfPublish.isEmpty()) {
                    Toast.makeText(EditActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (drawableId < 0 || drawableId > 5) {
                    Toast.makeText(EditActivity.this, "Image Id is between 0 and 5", Toast.LENGTH_SHORT).show();
                } else {
                    paintingDao.update(currentPainting);
                    Intent homeIntent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(EditActivity.this, "Image Id is empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}