package com.example.menudemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetailActivity extends AppCompatActivity {
    private ImageView image;
    private NotificationUtils notificationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        notificationUtils = new NotificationUtils(this);
        Painting painting = getIntent().getSerializableExtra("PAINTING", Painting.class);

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
            homeIntent.putExtra(MainActivity.PAINTING_ADDED_TO_CART, painting);
            notificationUtils.getManager().notify(200, notification.build());
            startActivity(homeIntent);
        });
    }

}