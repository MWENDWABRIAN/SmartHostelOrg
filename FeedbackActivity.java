package com.example.smarthostel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class FeedbackActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button feedbackButton;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        menu = findViewById(R.id.menu);
        feedbackButton = findViewById(R.id.buttonfeedback);
        ratingBar = findViewById(R.id.ratingbar);

        navigationView.setNavigationItemSelectedListener(this);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stars= String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(),stars+"Stars",Toast.LENGTH_LONG).show();
                sendFeedbackEmail();
            }
        });
    }

    private void sendFeedbackEmail() {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" +
                    Uri.encode("mwendwabrian979@gmail.com") +
                    "?subject=" +
                    Uri.encode("Feedback") +
                    "&body=" +
                    Uri.encode("Rating: " + ratingBar.getRating()); // Include the rating
            Uri uri = Uri.parse(uriText);
            intent.setData(uri);
            startActivity(Intent.createChooser(intent, "Send email"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle the case where there is no email client
            Toast.makeText(FeedbackActivity.this,
                    "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirectActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            redirectActivity(MainActivity.class);
        } else if (id == R.id.nav_hostels) {
            redirectActivity(HostelsActivity.class);
        } else if (id == R.id.nav_info) {
            redirectActivity(InfoActivity.class);
        } else if (id == R.id.nav_map) {
            redirectActivity(MapActivity.class);
        } else if (id == R.id.nav_feedback) {
            recreate();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
