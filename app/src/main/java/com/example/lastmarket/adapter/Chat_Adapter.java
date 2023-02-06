package com.example.lastmarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lastmarket.G;
import com.example.lastmarket.R;
import com.example.lastmarket.item.MessageItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat_Adapter extends BaseAdapter {

    Context context;
    ArrayList<MessageItem> items;

    public Chat_Adapter(Context context, ArrayList<MessageItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageItem item=items.get(position);

        //재활용할 뷰[convertView]는 사용하지 않을 것임 //채팅창을만들때 재활용뷰는만ㄷ르지않음
        View itemView=null;

        LayoutInflater inflater=LayoutInflater.from(context);

        //메세지가 내 메세지 인지..
        if( item.name.equals(G.userVo.name)){
            itemView= inflater.inflate(R.layout.my_messagebox, parent, false);
        }else{
            itemView= inflater.inflate(R.layout.other_messagebox, parent, false);
        }


        //bind view: 값 연결
        CircleImageView civ= itemView.findViewById(R.id.iv);
        TextView tvName= itemView.findViewById(R.id.tv_name);
        TextView tvMsg= itemView.findViewById(R.id.tv_msg);
        TextView tvTime= itemView.findViewById(R.id.tv_time);


        tvName.setText(item.name);
        tvMsg.setText(item.message);
        tvTime.setText(item.time);

        Glide.with(context).load(item.profileUrl).into(civ);

        return convertView;

    }
}
