package com.example.menudemo;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import java.util.HashSet;
import java.util.Set;

public class ItemDetailActivity extends AppCompatActivity {
    private static final int PHONE_CALL_PERMISSION_CODE = 100;
    AppDatabase database;
    private ImageView image;
    private NotificationUtils notificationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        database = AppDatabase.getDatabase(ItemDetailActivity.this);
        notificationUtils = new NotificationUtils(this);
//        Painting painting = getIntent().getSerializableExtra("PAINTING", Painting.class);
        Long id = getIntent().getLongExtra("PAINTING_ID", 1);
        PaintingDao paintingDao = database.getPaintingDao();
        PaintingEntity painting = paintingDao.getPaintingById(id);

        ((ImageView) findViewById(R.id.item_image)).setImageResource(painting.getDrawableId());
        ((TextView) findViewById(R.id.item_name)).setText(painting.getName());
        String artistAndDate = painting.getArtist() + " - " + painting.getDateOfPublish();
        ((TextView) findViewById(R.id.item_artist_and_date)).setText(artistAndDate);


        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener((l) -> {
            Intent homeIntent = new Intent(ItemDetailActivity.this, MainActivity.class);
            startActivity(homeIntent);
        });

        Button addToCartButton = findViewById(R.id.add_to_cart_button);
        addToCartButton.setOnClickListener(l -> {
            Notification.Builder notification = notificationUtils.getAndroidChannelNotification("Add to cart", painting.getName());
            Intent homeIntent = new Intent(ItemDetailActivity.this, MainActivity.class);
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            Set<String> cartSet = preferences.getStringSet("CART_LIST", new HashSet<>());
            cartSet.add(painting.getId().toString());
            editor.putStringSet("CART_LIST", cartSet);
            editor.apply();
            notificationUtils.getManager().notify(200, notification.build());
            startActivity(homeIntent);
        });

        Button callButton = findViewById(R.id.call_button);
        callButton.setOnClickListener(l -> {
            if (ContextCompat.checkSelfPermission(ItemDetailActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                String phoneNumber = "0905873874";
                Intent callPhoneIntent = new Intent(Intent.ACTION_CALL);
                callPhoneIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callPhoneIntent);
            } else {
                ActivityCompat.requestPermissions(ItemDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_PERMISSION_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PHONE_CALL_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String phoneNumber = "0905873874";
                Intent callPhoneIntent = new Intent(Intent.ACTION_CALL);
                callPhoneIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callPhoneIntent);
            } else {
                Toast.makeText(ItemDetailActivity.this, "Phone Call Permission Denied", Toast.LENGTH_LONG).show();

            }
        }
    }
}