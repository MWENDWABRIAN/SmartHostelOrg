package com.example.smarthostel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HostelsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu;

    CardView tumainiCard, delvicCard, ebenezerCard, pekaCard, hidayaCard, elshadaiCard, euvicsCard, dominionCard, majaliwaCard, pasisiCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostels);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        menu = findViewById(R.id.menu);

        // Initialize CardViews
        tumainiCard = findViewById(R.id.tumaini_card);
        delvicCard = findViewById(R.id.delvic_card);
        ebenezerCard = findViewById(R.id.ebenezer_card);
        pekaCard = findViewById(R.id.peka_card);
        hidayaCard = findViewById(R.id.hidaya_card);
        elshadaiCard = findViewById(R.id.elshadai_card);
        euvicsCard = findViewById(R.id.euvics_card);
        dominionCard = findViewById(R.id.dominion_card);
        majaliwaCard = findViewById(R.id.majaliwa_card);
        pasisiCard = findViewById(R.id.pasisi_card);

        // Set OnClickListeners for CardViews
        setCardClickListeners();

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
    }

    private void setCardClickListeners() {
        tumainiCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, TumainiActivity.class)));
        delvicCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, DelvicActivity.class)));
        ebenezerCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, EbenezerActivity.class)));
        pekaCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, PekaActivity.class)));
        hidayaCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, HidayaActivity.class)));
        elshadaiCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, ElshadaiActivity.class)));
        euvicsCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, EuvicsActivity.class)));
        dominionCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, DominionActivity.class)));
        majaliwaCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, MajaliwaActivity.class)));
        pasisiCard.setOnClickListener(v -> startActivity(new Intent(HostelsActivity.this, PasisiActivity.class)));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            redirectActivity(MainActivity.class);
        } else if (id == R.id.nav_hostels) {
            recreate();
        } else if (id == R.id.nav_info) {
            redirectActivity(InfoActivity.class);
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
}
