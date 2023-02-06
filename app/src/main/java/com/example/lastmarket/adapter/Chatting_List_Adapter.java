package com.example.lastmarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastmarket.R;
import com.example.lastmarket.activity.ChattingActivity;

import java.util.ArrayList;

public class Chatting_List_Adapter extends RecyclerView.Adapter<Chatting_List_Adapter.VH> {

    Context context;
    ArrayList<String> others;
    ArrayList<String> otherIds;

    public Chatting_List_Adapter(Context context, ArrayList<String> others, ArrayList<String> otherIds) {
        this.context = context;
        this.others = others;
        this.otherIds = otherIds;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.recycler_item_chatting_list,parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvChatOther.setText(others.get(position));
    }

    @Override
    public int getItemCount() {
        return others.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvChatOther;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvChatOther = itemView.findViewById(R.id.tv_chatting_list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String otherName = otherIds.get(pos);
                    Intent intent = new Intent(context, ChattingActivity.class);
                    intent.putExtra("otherName", otherName);
                    context.startActivity(intent);
                }
            });


        }
    }
}
