package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BarListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter2 myAdapter2;
    TextView barTextView;
    TextView cost;
    private DatabaseReference reference;
    private Button directionsBtn;

    ArrayList<Bars> bars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_list);

        recyclerView = findViewById(R.id.barListRecyclerView);
        barTextView = findViewById(R.id.barTextViewRow);
        cost = findViewById(R.id.numberText);
        directionsBtn = findViewById(R.id.directionsBtn);
        reference = FirebaseDatabase.getInstance().getReference();

        myAdapter2 = new MyAdapter2(this, bars);
        recyclerView.setAdapter(myAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                calculateCosts(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barLocation;
                if(myAdapter2.getSelectedBar() != null){
                    barLocation = myAdapter2.getSelectedBar().getBarsAddress();
                    Intent mapsIntent = new Intent(getApplicationContext(), MapsActivity.class);
                    mapsIntent.putExtra("bar_location", barLocation);
                    startActivity(mapsIntent);
                }
                else{
                    showToast("No Selection");
                }

            }
        });
    }

    private void calculateCosts(DataSnapshot dataSnapshot){
        ArrayList<String> drinkList = new ArrayList<>();
        if(getIntent().hasExtra("drinks_list")){
            drinkList = getIntent().getStringArrayListExtra("drinks_list");
        }

       for(DataSnapshot ds : dataSnapshot.getChildren()){
           int childrenCount = (int)ds.getChildrenCount() + 1;
           String[][][] infoArray = new String[childrenCount][childrenCount][childrenCount];

           for(int i = 1; i < (childrenCount); i++) {
               String childBar = Integer.toString(i);
               infoArray[i][0][0] = ds.child(childBar).child("name").getValue().toString();
               infoArray[0][i][0] = ds.child(childBar).child("address").getValue().toString();
               Double costsTotal = 0.00;
               for(int j = 0; j < drinkList.size(); j++) {
                   String costs = ds.child(childBar).child(drinkList.get(j).toLowerCase()).getValue().toString();
                   double drinkCost = Double.parseDouble(costs);
                   costsTotal += drinkCost;
               }
               infoArray[0][0][i] = String.valueOf(Math.round(costsTotal*100.0)/100.0);

           }

           double[] costsArray = new double[childrenCount];
           for(int k = 1; k < childrenCount; k++){
               costsArray[k] = Double.valueOf(infoArray[0][0][k]);
           }
           costsArray = bubbleSort(costsArray);
           bars = new ArrayList<>();
           for(int h = 1; h < childrenCount; h++){
               if(costsArray[1] == Double.valueOf(infoArray[0][0][h])){
                   Bars bar = new Bars();
                   bar.setBarsCost(costsArray[1]);
                   bar.setBarsName(infoArray[h][0][0]);
                   bar.setBarsAddress(infoArray[0][h][0]);
                   bars.add(bar);
               }
               myAdapter2.setBars(bars);
           }

       }
   }


   private static double[] bubbleSort(double[] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array.length-1-i; j++){
                if(array[j] > array[j+1]){
                    double temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        return array;
   }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
