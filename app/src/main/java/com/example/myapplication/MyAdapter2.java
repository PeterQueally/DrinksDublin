package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {

    Context context;
    private ArrayList<Bars> bars;
    private int checkedPosition = -1;

    public MyAdapter2(Context context, ArrayList<Bars> bars){
        this.context = context;
        this.bars = bars;
    }

    @NonNull
    @Override
    public MyAdapter2.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bar_list_row, parent, false);
        return new MyViewHolder2(view);
    }

    public void setBars(ArrayList<Bars> bars) {
        this.bars = new ArrayList<>();
        this.bars = bars;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        holder.bind(bars.get(position));
    }

    @Override
    public int getItemCount() {
        return bars.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView barTextView;
        TextView cost;
        ImageView tickViewBar;

        public MyViewHolder2(@NonNull View itemView){
            super(itemView);
            barTextView = itemView.findViewById(R.id.barTextViewRow);
            cost = itemView.findViewById(R.id.numberText);
            tickViewBar = itemView.findViewById(R.id.tickViewBar);
        }

        void bind(final Bars bars){
            if(checkedPosition == -1){
                tickViewBar.setVisibility(View.GONE);
            }
            else{
                if(checkedPosition == getAdapterPosition()){
                    tickViewBar.setVisibility(View.VISIBLE);
                }
                else{
                    tickViewBar.setVisibility(View.GONE);
                }
            }
            barTextView.setText(bars.getBarsName());

            DecimalFormat dc = new DecimalFormat("#.00");
            String f = dc.format(bars.getBarsCost());
            cost.setText(f);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tickViewBar.setVisibility(view.VISIBLE);
                    if(checkedPosition != getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public Bars getSelectedBar(){
        if(checkedPosition != -1){
            return bars.get(checkedPosition);
        }
        return null;
    }
}
