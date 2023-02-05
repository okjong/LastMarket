package com.example.lastmarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastmarket.R;
import com.example.lastmarket.fragment.Tab01_HomeFragment;
import com.example.lastmarket.fragment.Tab02_RecommendationFragment;
import com.example.lastmarket.fragment.Tab03_CategoryFragment;
import com.example.lastmarket.fragment.Tab04_SearchFragment;
import com.example.lastmarket.fragment.Tab05_MypurpleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kakao.sdk.common.util.Utility;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    Fragment[] fragments= new Fragment[5];
    FragmentManager fragmentManager;
    TextView tv;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu_01,menu);
        getMenuInflater().inflate(R.menu.topmenu_02,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if (id==R.id.menu_setting){
            Intent intent= new Intent(this,MapActivity.class);
            startActivity(intent);
        }
        if (id==R.id.menu_setting2){
            Intent intent= new Intent(this,CatchActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String keyhash= Utility.INSTANCE.getKeyHash(this);
//        Log.i("keyhash",keyhash);

        tv=findViewById(R.id.tv);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fragmentManager=getSupportFragmentManager();

        //Home 기본화면설정
        FragmentTransaction tran=fragmentManager.beginTransaction();
        fragments[0] = new Tab01_HomeFragment();
        tv.setText("Market");
        tran.add(R.id.container,fragments[0]);
        tran.commit();

        bnv=findViewById(R.id.bnv);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction tran= fragmentManager.beginTransaction();
                if (fragments[0]!=null) tran.hide(fragments[0]);
                if (fragments[1]!=null) tran.hide(fragments[1]);
                if (fragments[2]!=null) tran.hide(fragments[2]);
                if (fragments[3]!=null) tran.hide(fragments[3]);
                if (fragments[4]!=null) tran.hide(fragments[4]);

                switch (item.getItemId()){


                    case  R.id.bnv_home:
                        tv.setText("Market");
                        tran.show(fragments[0]);
                        break;

                    case  R.id.bnv_recommendation:
                        if(fragments[1]==null){
                            fragments[1]= new Tab02_RecommendationFragment();
                            tran.add(R.id.container,fragments[1]);
                        }
                        tv.setText("추천");
                        tran.show(fragments[1]);
                        break;

                    case  R.id.bnv_category:
                        if(fragments[2]==null){
                            fragments[2]= new Tab03_CategoryFragment();
                            tran.add(R.id.container,fragments[2]);
                        }
                        tv.setText("카테고리");
                        tran.show(fragments[2]);
                        break;

                    case  R.id.bnv_search:
                        if(fragments[3]==null){
                            fragments[3]= new Tab04_SearchFragment();
                            tran.add(R.id.container,fragments[3]);
                        }
                        tv.setText("검색");
                        tran.show(fragments[3]);
                        break;

                    case  R.id.bnv_mypurple:
                        if(fragments[4]==null){
                            fragments[4]= new Tab05_MypurpleFragment();
                            tran.add(R.id.container,fragments[4]);
                        }
                        tv.setText("Login");
                        tran.show(fragments[4]);
                        break;

                }
                tran.commit();

                return true;


            }
        });
    }
}