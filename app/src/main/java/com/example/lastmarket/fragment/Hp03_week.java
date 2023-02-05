package com.example.lastmarket.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastmarket.R;
import com.example.lastmarket.RetroService02;
import com.example.lastmarket.activity.RetrofitHelper;
import com.example.lastmarket.adapter.Home_Recycler03_Adapter;
import com.example.lastmarket.item.Home_Recycler03_item;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Hp03_week extends Fragment {

    ArrayList<Home_Recycler03_item> items= new ArrayList<Home_Recycler03_item>();
    RecyclerView recyclerView;
    Home_Recycler03_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page03_week,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= view.findViewById(R.id.recycler03);
        adapter=new Home_Recycler03_Adapter(getContext(),items);
        recyclerView.setAdapter(adapter);

        String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ActivityCompat.checkSelfPermission(getContext(),permissions[0])== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(),permissions,100);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    void loadData(){
        Retrofit retrofit= RetrofitHelper.getRetrofitInstanceGson();
        RetroService02 retroService02=retrofit.create(RetroService02.class);
        Call<ArrayList<Home_Recycler03_item>> call=retroService02.loadDataFromServer();
        call.enqueue(new Callback<ArrayList<Home_Recycler03_item>>() {
            @Override
            public void onResponse(Call<ArrayList<Home_Recycler03_item>> call, Response<ArrayList<Home_Recycler03_item>> response) {
                items.clear();
                adapter.notifyDataSetChanged();

                ArrayList<Home_Recycler03_item> list= response.body();
                for (Home_Recycler03_item item:list){
                    items.add(0,item);
                    adapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Home_Recycler03_item>> call, Throwable t) {
                Toast.makeText(getContext(), "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
