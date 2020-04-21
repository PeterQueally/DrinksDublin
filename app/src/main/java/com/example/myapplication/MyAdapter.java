package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    int drinkImages[];
    Context context;
    private ArrayList<Drinks> drinks;


    public MyAdapter(Context ct, ArrayList<Drinks> drinks,  int images[]){

        context = ct;
        drinkImages = images;
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.drinks_list_row, parent,false);
        return new MyViewHolder(view);
    }

    public void setDrinks(ArrayList<Drinks> drinks) {
        this.drinks = new ArrayList<>();
        this.drinks = drinks;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.bind(drinks.get(position));
        holder.drinkImage.setImageResource(drinkImages[position]);
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView drinkTitle;
        ImageView drinkImage;
        ImageView tickView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tickView = itemView.findViewById(R.id.tickView);
            drinkTitle = itemView.findViewById(R.id.drinkTextViewRow);
            drinkImage = itemView.findViewById(R.id.drinkImageRow);
        }

        void bind(final Drinks drinks) {
            tickView.setVisibility(drinks.isChecked() ? View.VISIBLE : View.GONE);
            drinkTitle.setText(drinks.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drinks.setChecked(!drinks.isChecked());
                    tickView.setVisibility(drinks.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<Drinks> getSelected() {
        ArrayList<Drinks> selected = new ArrayList<>();
        for (int i = 0; i < drinks.size(); i++) {
            if (drinks.get(i).isChecked()) {
                selected.add(drinks.get(i));
            }
        }
        return selected;
    }
}
