package com.example.chohee.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account_bnt_active extends android.support.v4.app.Fragment {


    private ListView listView=null;
    private  ArrayAdapter<String> account_data=null;
    List<Map<String, String>> planetsList = new ArrayList<Map<String,String>>();
    WebView webview=null;



    private HashMap<String, String> createPlanet(String key, String name) {
        HashMap<String, String> planet = new HashMap<String, String>();
        planet.put(key, name);

        return planet;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TextView textView = (TextView) view.findViewById(R.id.hello);
        //textView.setTextSize(80);
        listView = (ListView) view.findViewById(R.id.listView);
        SimpleAdapter simpleAdpt = new SimpleAdapter(this.getContext(), planetsList, android.R.layout.simple_list_item_1, new String[] {"planet"}, new int[] {android.R.id.text1});
        webview=(WebView) view.findViewById(R.id.webView);
        webview.setWebViewClient(new WebClient());
        WebSettings setting=webview.getSettings();
        setting.setJavaScriptEnabled(true);



        listView.setAdapter(simpleAdpt);

        initList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {

// We know the View is a TextView so we can cast it
                TextView clickedView = (TextView) view;
                Toast.makeText(getActivity(), "Item with id [" + id + "] - Position [" + position + "] - Planet [" + clickedView.getText() + "]", Toast.LENGTH_SHORT).show();
                webview.loadUrl("http://finance.daum.net/exchange/exchangeMain.daum?DA=TMZ");

            }
        });

        registerForContextMenu(listView);



    }

    private void initList() {
// We populate the planets

        planetsList.add(createPlanet("planet", "Mercury"));
        planetsList.add(createPlanet("planet", "Venus"));
        planetsList.add(createPlanet("planet", "Mars"));
        planetsList.add(createPlanet("planet", "Jupiter"));
        planetsList.add(createPlanet("planet", "Saturn"));
        planetsList.add(createPlanet("planet", "Uranus"));
        planetsList.add(createPlanet("planet", "Neptune"));

    }


    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getClass().getName(), "oncreateView");
        return inflater.inflate(R.layout.account_btn_active,container,false);
    }

    class WebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}




