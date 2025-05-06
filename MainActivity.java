
package com.example.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends Activity {
    TextView txtExpense, txtIncome, txtNet, btnDate;
    Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtExpense = findViewById(R.id.txtExpense);
        txtIncome = findViewById(R.id.txtIncome);
        txtNet = findViewById(R.id.txtNet);
        btnDate = findViewById(R.id.btnDate);
        btnEnter = findViewById(R.id.btnEnter);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        btnDate.setText("الشهر: " + month);

        btnEnter.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EntryActivity.class);
            startActivity(intent);
        });
    }
}
