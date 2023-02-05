package com.example.lastmarket.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastmarket.LinearLayoutManagerWrapper;
import com.example.lastmarket.R;
import com.example.lastmarket.adapter.Recommendation_Recycler01_Adapter;
import com.example.lastmarket.item.Recommendation_Recycler01_item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Tab02_RecommendationFragment extends Fragment {

    ArrayList<Recommendation_Recycler01_item> items = new ArrayList<Recommendation_Recycler01_item>();
    RecyclerView recyclerView;
    Recommendation_Recycler01_Adapter adapter;
    Button loadBtn;


    // 사이트에서 발급받은 key
    String apiKey = "6e6e7a4c59616b77393045454a5249";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2_recommendation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler04);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new Recommendation_Recycler01_Adapter(getContext(), items);
        recyclerView.setAdapter(adapter);

        loadBtn = view.findViewById(R.id.R_loadData);


        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.clear();
                new Thread() {
                    @Override
                    public void run() {
                        String address = "http://openapi.seoul.go.kr:8088/"
                                + apiKey
                                + "/xml/ListPriceModelStoreProductService/1/30/";
                        try {
                            URL url = new URL(address);
                            InputStream is = url.openStream();
                            InputStreamReader isr = new InputStreamReader(is);

                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); //어렵네 일단은그냥 외우자 ㅠㅠ 분석을그냥할수없어서 공장을만들어서 그공장을통해서 분석해야됨!
                            XmlPullParser xpp = factory.newPullParser();
                            xpp.setInput(isr);
                            int eventType = xpp.getEventType();
                            StringBuffer buffer = null;

                            boolean isFinish;
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                isFinish = false;
                                Recommendation_Recycler01_item item = new Recommendation_Recycler01_item();
                                while (isFinish == false) {
                                    switch (eventType) {
                                        case XmlPullParser.START_DOCUMENT:
                                            break;

                                        case XmlPullParser.START_TAG:
                                            String tagName = xpp.getName();
                                            if (tagName.equals("row")) {



                                            } else if (tagName.equals("SH_NAME")) {

                                                xpp.next();
                                                item.title = xpp.getText();

                                            } else if (tagName.equals("SH_ADDR")) {

                                                xpp.next();
                                                item.address = xpp.getText();

                                            } else if (tagName.equals("IM_NAME")) {

                                                xpp.next();
                                                item.name = xpp.getText();


                                            } else if (tagName.equals("IM_PRICE")) {

                                                xpp.next();
                                                item.price = xpp.getText()+"원";
                                                isFinish = true;

                                            }
                                            break;

                                        case XmlPullParser.TEXT:
                                            break;

                                        case XmlPullParser.END_TAG:
                                            String tagName2 = xpp.getName();
                                            if (tagName2.equals("row")) {

                                            }

                                            break;

                                    }

                                    eventType = xpp.next();

                                }
                                items.add(item);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyItemInserted(items.size()-1);
                                    }
                                });

                            }


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }



                    }


                }.start();


            }
        });
    }
}
