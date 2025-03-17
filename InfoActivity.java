package com.example.smarthostel;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class InfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu;
    ImageView whatsappHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        menu = findViewById(R.id.menu);
        whatsappHelp = findViewById(R.id.whatsappHelp);

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

        whatsappHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "254794761574"; // Replace with your WhatsApp number
                String message = "For any help, please click the icon.";
                openWhatsApp(phoneNumber, message);
            }
        });

        // Underline the "ABOUT US" TextView
        TextView aboutUsTextView = findViewById(R.id.aboutUsText); // Replace with your TextView ID
        if (aboutUsTextView != null) {
            aboutUsTextView.setPaintFlags(aboutUsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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
            recreate();
        } else if (id == R.id.nav_map) {
            redirectActivity(MapActivity.class);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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

    private void openWhatsApp(String phoneNumber, String message) {
        try {
            Uri uri = Uri.parse("smsto:" + phoneNumber);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            i.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(i, ""));
        } catch (android.content.ActivityNotFoundException ex) {
            // WhatsApp is not installed
            Uri uri = Uri.parse("smsto:" + phoneNumber);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(i);
        }
    }
}
