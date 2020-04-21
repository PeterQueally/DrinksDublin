package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.MyAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class DrinksListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    MyAdapter myAdapter;
    private ArrayList<Drinks> drinks = new ArrayList<>();
    private Button priceCompareBtn;

    String[] drinkTitles;
    int drinkImages[] = {R.drawable.budweiser,R.drawable.carlsberg,R.drawable.coorslight,R.drawable.corona
            ,R.drawable.desperado,R.drawable.guinness,R.drawable.heineken,R.drawable.miller,R.drawable.peroni,R.drawable.tiger};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_list);

        recyclerView = findViewById(R.id.recyclerViewDrinksList);
        priceCompareBtn = findViewById(R.id.priceCompareBtn);
        drinkTitles = getResources().getStringArray(R.array.drinkTitles);

        myAdapter = new MyAdapter(this, drinks, drinkImages);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createList();

        priceCompareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selectedDrinksList = new ArrayList<>();
                if (myAdapter.getSelected().size() != 0){
                    for (int i = 0; i < myAdapter.getSelected().size(); i++) {
                         selectedDrinksList.add(myAdapter.getSelected().get(i).getName());
                      }
                    Intent barListIntent = new Intent(getApplicationContext(),BarListActivity.class);
                    barListIntent.putStringArrayListExtra("drinks_list",selectedDrinksList);
                    startActivity(barListIntent);
                }
                else{
                    showToast("No Selection");
                }
            }
        });

    }
    private void createList(){
        drinks = new ArrayList<>();
        for (int i = 0; i < drinkTitles.length; i++) {
            Drinks drink = new Drinks();
            drink.setName(drinkTitles[i]);
            drinks.add(drink);
        }
        myAdapter.setDrinks(drinks);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
