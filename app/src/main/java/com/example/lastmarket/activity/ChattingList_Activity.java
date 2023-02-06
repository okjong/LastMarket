package com.example.lastmarket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lastmarket.G;
import com.example.lastmarket.R;
import com.example.lastmarket.adapter.Chatting_List_Adapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChattingList_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    Chatting_List_Adapter adapter;
    ArrayList<String> others;
    ArrayList<String> otherIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_list);
        recyclerView = findViewById(R.id.recycler_chatting_list);
        others = new ArrayList<>();
        otherIds = new ArrayList<>();
        adapter = new Chatting_List_Adapter(this, others, otherIds);
        recyclerView.setAdapter(adapter);
        loadOthers();
    }

    void loadOthers(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("chat").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().split("&")[0].equals(G.userVo.id)){
                        FirebaseDatabase.getInstance().getReference("user").child(ds.getKey().split("&")[1]).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
//                                Toast.makeText(ChattingList.this, "" + dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                                otherIds.add(dataSnapshot.getKey());
                                others.add(dataSnapshot.getValue(String.class));
                                adapter.notifyItemInserted(others.size()-1);
                            }
                        });
                    }
                }
            }
        });
    }

}