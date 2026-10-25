package com.example.hoteldine;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        String hotel = getIntent().getStringExtra("hotelName");
        String items = getIntent().getStringExtra("items");
        int total = getIntent().getIntExtra("total", 0);

        ((TextView)findViewById(R.id.orderHotel)).setText(hotel);
        ((TextView)findViewById(R.id.orderItems)).setText(items);
        ((TextView)findViewById(R.id.orderTotal)).setText("Total: ₹" + total);

        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show();
        findViewById(R.id.doneButton).setOnClickListener(v -> finish());
    }
}
