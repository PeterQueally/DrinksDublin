package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class InputPriceActivity extends AppCompatActivity {

    private ArrayList<String> barNameList, drinkNameList;
    private BarSpinnerAdapter barSpinnerAdapter, drinkSpinnerAdapter;
    private Spinner barNameSpinner, drinkNameSpinner;
    private EditText priceInputText;
    private Button priceInputBtn;
    private String selectedBarName, selectedDrinkName;


    private DatabaseReference dBreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_price);

        drinkNameList = new ArrayList<>();
        String[] drinksArray = getResources().getStringArray(R.array.drinkTitles);

        for(int i = 0; i < drinksArray.length; i++){
            drinkNameList.add(drinksArray[i]);
        }

        dBreference = FirebaseDatabase.getInstance().getReference();
        barNameSpinner = findViewById(R.id.spinnerBars);
        drinkNameSpinner = findViewById(R.id.spinnerDrinks);
        priceInputText = findViewById(R.id.priceInputEdit);
        priceInputBtn = findViewById(R.id.priceInputBtn);
        priceInputText = findViewById(R.id.priceInputEdit);

        drinkSpinnerAdapter = new BarSpinnerAdapter(this, drinkNameList);
        drinkNameSpinner.setAdapter(drinkSpinnerAdapter);

        dBreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                initList(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        barNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBarName = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        drinkNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDrinkName = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        priceInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(priceInputText.getText().toString().matches("\\d+\\.\\d\\d"))
                {
                    String[] infoArray = new String[3];
                    infoArray[0] = selectedDrinkName;
                    infoArray[1] = selectedBarName;
                    infoArray[2] = priceInputText.getText().toString();
                    Intent inputInfoIntent = new Intent(getApplicationContext(), ThankYouActivity.class);
                    inputInfoIntent.putExtra("info_list", infoArray);
                    startActivity(inputInfoIntent);
                }
                else
                    {
                    Toast.makeText(InputPriceActivity.this,"Please enter a number in the format 5.00",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initList(DataSnapshot dataSnapshot){
        barNameList = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            int childrenCount = (int)ds.getChildrenCount() + 1;
            for(int i = 1; i < childrenCount; i++){
                String childBar = Integer.toString(i);
                barNameList.add(ds.child(childBar).child("name").getValue().toString());
            }
        }
        Collections.sort(barNameList);
        barSpinnerAdapter = new BarSpinnerAdapter(this,barNameList);
        barNameSpinner.setAdapter(barSpinnerAdapter);
    }
}
