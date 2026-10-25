package com.example.hoteldine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private EditText searchBox;
    private LinearLayout hotelContainer;

    private final String[][] hotels = {
        {"Grand Palace", "4.5", "₹2500 / night", "Luxury hotel with a restaurant"},
        {"Royal Residency", "4.2", "₹1800 / night", "Comfortable rooms and family dining"},
        {"City View Hotel", "4.4", "₹2200 / night", "Modern rooms with city views"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("hotel_prefs", MODE_PRIVATE);
        searchBox = findViewById(R.id.searchBox);
        hotelContainer = findViewById(R.id.hotelContainer);

        String lastHotel = prefs.getString("lastHotel", "");
        if (!lastHotel.isEmpty()) {
            Toast.makeText(this, "Last viewed: " + lastHotel, Toast.LENGTH_LONG).show();
        }

        showHotels("");
        findViewById(R.id.searchButton).setOnClickListener(v ->
            showHotels(searchBox.getText().toString().trim()));
    }

    private void showHotels(String query) {
        hotelContainer.removeAllViews();

        for (String[] hotel : hotels) {
            if (!query.isEmpty() &&
                !hotel[0].toLowerCase().contains(query.toLowerCase())) continue;

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(24, 22, 24, 22);
            card.setBackgroundResource(R.drawable.card_background);

            TextView name = new TextView(this);
            name.setText("🏨  " + hotel[0]);
            name.setTextSize(21);
            name.setTypeface(null, 1);

            TextView details = new TextView(this);
            details.setText("⭐ " + hotel[1] + "     " + hotel[2] + "\n" + hotel[3]);
            details.setTextSize(16);
            details.setPadding(0, 12, 0, 12);

            Button menu = new Button(this);
            menu.setText("View Menu");
            menu.setOnClickListener(v -> {
                prefs.edit().putString("lastHotel", hotel[0]).apply();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("hotelName", hotel[0]);
                startActivity(intent);
            });

            card.addView(name);
            card.addView(details);
            card.addView(menu);

            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
            p.setMargins(0, 0, 0, 22);
            hotelContainer.addView(card, p);
        }
    }
}
