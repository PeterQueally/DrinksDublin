package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThankYouActivity extends AppCompatActivity {

    private String barNumber, selectedBarName, selectedDrinkName, priceOfDrink;
    private DatabaseReference dBreference;
    private TextView thankYouText;
    private String[] infoArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        thankYouText = findViewById(R.id.thankYouTextView);
        dBreference = FirebaseDatabase.getInstance().getReference();
        infoArray = new String[3];
        if(getIntent().hasExtra("info_list")){
            infoArray = getIntent().getStringArrayExtra("info_list");
        }
        selectedDrinkName = infoArray[0];
        selectedBarName = infoArray[1];
        priceOfDrink = infoArray[2];
        dBreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getSelectedBar(dataSnapshot);
                dBreference.child("BarMembers").child(barNumber).child(selectedDrinkName.toLowerCase()).setValue(Double.valueOf(priceOfDrink));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        thankYouText.setText("Thank you for updating the price of " + selectedDrinkName + " in " + selectedBarName + " to the price of €" + priceOfDrink + "!");
    }

    private void getSelectedBar(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            int childrenCount = (int)ds.getChildrenCount() + 1;
            for(int i = 1; i < childrenCount; i++){
                String childBar = Integer.toString(i);
                if(ds.child(childBar).child("name").getValue().toString().equals(selectedBarName)){
                    barNumber = childBar;
                }
            }
        }
    }
}
