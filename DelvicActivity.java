package com.example.smarthostel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DelvicActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delvic);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        setupRoomCard(R.id.cardView1, R.id.mapdirection, R.id.calldelvic, R.id.bookButton, "Room 1", "0712345678", "room1_booked", "room1_called");
        setupRoomCard(R.id.cardView2, R.id.mapdirection2, R.id.calldelvic2, R.id.bookButton2, "Room 2", "0712345679", "room2_booked", "room2_called");
        setupRoomCard(R.id.cardView3, R.id.mapdirection3, R.id.calldelvic3, R.id.bookButton3, "Room 3", "0712345680", "room3_booked", "room3_called");
        setupRoomCard(R.id.cardView4, R.id.mapdirection4, R.id.calldelvic4, R.id.bookButton4, "Room 4", "0712345681", "room4_booked", "room4_called");
        setupRoomCard(R.id.cardView5, R.id.mapdirection5, R.id.calldelvic5, R.id.bookButton5, "Room 5", "0712345682", "room5_booked", "room5_called");
        setupRoomCard(R.id.cardView6, R.id.mapdirection6, R.id.calldelvic6, R.id.bookButton6, "Room 6", "0712345683", "room6_booked", "room6_called");
        setupRoomCard(R.id.cardView7, R.id.mapdirection7, R.id.calldelvic7, R.id.bookButton7, "Room 7", "0712345684", "room7_booked", "room7_called");
        setupRoomCard(R.id.cardView8, R.id.mapdirection8, R.id.calldelvic8, R.id.bookButton8, "Room 8", "0712345685", "room8_booked", "room8_called");
    }

    private void setupRoomCard(int cardViewId, int mapDirectionId, int callDelvicId, int bookButtonId, String roomName, String phoneNumber, String bookedKey, String calledKey) {
        CardView cardView = findViewById(cardViewId);
        ImageView mapDirection = cardView.findViewById(mapDirectionId);
        ImageView callDelvic = cardView.findViewById(callDelvicId);
        Button bookButton = cardView.findViewById(bookButtonId);

        boolean isBooked = sharedPreferences.getBoolean(bookedKey, false);
        boolean isCalled = sharedPreferences.getBoolean(calledKey, false);

        updateButtonState(bookButton, isBooked, isCalled);

        mapDirection.setOnClickListener(v -> {
            Intent mapIntent = new Intent(DelvicActivity.this, MapActivity.class);
            startActivity(mapIntent);
        });

        callDelvic.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);

            // Mark the call as made
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(calledKey, true);
            editor.apply();

            // Update button visibility
            updateButtonState(bookButton, isBooked, true);
        });

        bookButton.setOnClickListener(v -> {
            boolean currentBookedState = sharedPreferences.getBoolean(bookedKey, false);
            boolean newBookedState = !currentBookedState;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(bookedKey, newBookedState);
            editor.apply();
            updateButtonState(bookButton, newBookedState, true); //Called must be true when booking.
        });
    }

    private void updateButtonState(Button button, boolean isBooked, boolean isCalled) {
        if (isBooked) {
            button.setText("Booked");
            button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.RED));
            button.setVisibility(View.VISIBLE);
            button.setEnabled(false); // Disable when booked
        } else {
            button.setEnabled(true); // Enable when not booked
            if (isCalled) {
                button.setText("Book Room");
                button.setBackgroundTintList(getResources().getColorStateList(R.color.Orange, getTheme()));
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }
}
