package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.BarDatabase;
import com.example.myapplication.DrinksListActivity;
import com.example.myapplication.InputPriceActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    ImageView helpImgView, helpImgView2, mainImage ;
    Button buttonMain1, buttonMain2;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helpImgView = findViewById(R.id.helpImgView);
        helpImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Welcome to Drinks Dublin! Select multiple drinks in the drink selector menu to " +
                        "find the bar with the cheapest total of those drinks in Dublin!", Toast.LENGTH_LONG).show();
            }
        });

        helpImgView2 = findViewById(R.id.helpImgView2);
        helpImgView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Help keep the database updated by inputting prices of drinks in bars that you " +
                        "visit! Choose the drink you want to price and the bar it is in and enter the price in the format '5.00'. Thank you!", Toast.LENGTH_LONG).show();
            }
        });

        buttonMain1 = findViewById(R.id.priceList);
        buttonMain1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drinksListActivity = new Intent(getApplicationContext(), DrinksListActivity.class);
                startActivity(drinksListActivity);
            }
        });

        buttonMain2 = findViewById(R.id.inputPrice);
        buttonMain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inputPriceActivity = new Intent(getApplicationContext(), InputPriceActivity.class);
                startActivity(inputPriceActivity);
            }
        });

         /*Method to input data to database.

        BarDatabase bar=  new BarDatabase("12 Fleet St, Temple Bar, Dublin", "Hard Rock Cafe", 4.85, 4.95, 4.80, 5.25,
                4.95, 4.15, 4.75, 5.10, 4.45, 5.60);
        dbReference = FirebaseDatabase.getInstance().getReference("BarMembers");
        dbReference.child("10").setValue(bar); */


    }
}
