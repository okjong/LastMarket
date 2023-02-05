package com.example.lastmarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.lastmarket.R;
import com.example.lastmarket.item.Home_Recycler03_item;

import java.util.ArrayList;

public class Home_Recycler03_Adapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Home_Recycler03_item> items;

    public Home_Recycler03_Adapter(Context context, ArrayList<Home_Recycler03_item> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.home_recycler03_item,parent,false);
        VH holder= new VH(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        VH vh=(VH)holder;

         Home_Recycler03_item item=items.get(position);

    //이미지 설정[DB]에는 이미지경로가 "./uploads/IMG_20210240_moana01.jpg"임
        //안드로이드에서는 서버(dothome)의 전체 주소가 필요하기에
        String imgUrl="http://jeilpharm.dothome.co.kr/Market02/"+item.file;
        Glide.with(context).load(imgUrl).into(((VH) holder).ivImg);



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        ImageView ivImg;


        public VH(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "상세페이지 준비중입니다.", Toast.LENGTH_SHORT).show();
                }
            });
            ivImg=itemView.findViewById(R.id.iv);




        }
    }
}
