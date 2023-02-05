package com.example.lastmarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastmarket.R;
import com.example.lastmarket.item.Recommendation_Recycler01_item;

import java.util.ArrayList;

public class Recommendation_Recycler01_Adapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<Recommendation_Recycler01_item> items;

    public Recommendation_Recycler01_Adapter(Context context, ArrayList<Recommendation_Recycler01_item> arrayList) {
        this.context = context;
        this.items = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.recommend_recycler01_item,parent,false);
        VH holder= new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH)holder;
        Recommendation_Recycler01_item item=items.get(position);

        vh.R_title.setText(item.title);
        vh.R_name.setText(item.name);
        vh.R_price.setText(item.price);
        vh.R_address.setText(item.address);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView R_title;
        TextView R_name;
        TextView R_price;
        TextView R_address;


        public VH(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "상세페이지 준비중입니다.", Toast.LENGTH_SHORT).show();
                }
            });
            this.R_title=itemView.findViewById(R.id.tv_title);
            this.R_name=itemView.findViewById(R.id.tv_name);
            this.R_price=itemView.findViewById(R.id.tv_price);
            this.R_address=itemView.findViewById(R.id.tv_address);
        }
    }

}
