package com.example.hoteldine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private LinearLayout menuContainer;
    private TextView totalText;
    private final ArrayList<String> selectedItems = new ArrayList<>();
    private int total = 0;
    private String hotelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        hotelName = getIntent().getStringExtra("hotelName");
        if (hotelName == null) hotelName = "Grand Palace";

        ((TextView)findViewById(R.id.hotelTitle)).setText(hotelName);
        menuContainer = findViewById(R.id.menuContainer);
        totalText = findViewById(R.id.totalText);

        showDemoMenu();

        findViewById(R.id.placeOrderButton).setOnClickListener(v -> {
            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "Please add at least one item.", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("hotel_prefs", MODE_PRIVATE);
            prefs.edit()
                .putString("lastOrderHotel", hotelName)
                .putString("lastOrderItems", String.join(", ", selectedItems))
                .putInt("lastOrderTotal", total)
                .apply();

            Intent intent = new Intent(MenuActivity.this, OrderActivity.class);
            intent.putExtra("hotelName", hotelName);
            intent.putExtra("items", String.join(", ", selectedItems));
            intent.putExtra("total", total);
            startActivity(intent);
        });
    }

    private void showDemoMenu() {
        addMenuItem("Margherita Pizza", "₹299", 299);
        addMenuItem("Classic Burger", "₹199", 199);
        addMenuItem("Alfredo Pasta", "₹249", 249);
        addMenuItem("Fresh Lime Soda", "₹99", 99);
    }

    private void addMenuItem(String name, String priceText, int price) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(10, 14, 10, 14);

        LinearLayout info = new LinearLayout(this);
        info.setOrientation(LinearLayout.VERTICAL);

        TextView itemName = new TextView(this);
        itemName.setText(name);
        itemName.setTextSize(18);
        itemName.setTypeface(null, 1);

        TextView itemPrice = new TextView(this);
        itemPrice.setText(priceText);
        itemPrice.setTextSize(16);

        info.addView(itemName);
        info.addView(itemPrice);

        Button add = new Button(this);
        add.setText("Add");
        add.setOnClickListener(v -> {
            selectedItems.add(name);
            total += price;
            totalText.setText("Items: " + selectedItems.size() + "    Total: ₹" + total);
            Toast.makeText(this, name + " added", Toast.LENGTH_SHORT).show();
        });

        row.addView(info, new LinearLayout.LayoutParams(0,
            LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        row.addView(add);
        menuContainer.addView(row);
    }
}
