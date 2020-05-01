package com.example.payments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Ui components
    Button btnProfits, btnExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnProfits = findViewById(R.id.btnProfits);
        btnExpenses = findViewById(R.id.btnExpenses);
        btnProfits.setOnClickListener(v->{
            Intent intent = new Intent(this, ProfitsActivity.class);
            startActivity(intent);
        });
        btnExpenses.setOnClickListener(v->{
            Intent intent = new Intent(this, ExpensesActivity.class);
            startActivity(intent);
        });
    }
}
