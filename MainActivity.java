package com.example.com.myapplication2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;


public class MainActivity extends AppCompatActivity {
//  static final LatLng SEOUL = new LatLng( 37.56, 126.97);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LatLng KANGNAM = new LatLng(37.5172360, 127.0473250);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.addMarker(new MarkerOptions().position(KANGNAM).title("ggari").snippet("hello"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(KANGNAM, 15));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabHost th = (TabHost) findViewById(R.id.tabHost);
        th.setup();

        TabHost.TabSpec sp1 = th.newTabSpec("tab1");
        sp1.setIndicator(getString(R.string.tab1));
        sp1.setContent(R.id.tab1);
        th.addTab(sp1);

        TabHost.TabSpec sp2 = th.newTabSpec("tab2");
        sp2.setIndicator(getString(R.string.tab2));
        sp2.setContent(R.id.tab2);
        th.addTab(sp2);

        TabHost.TabSpec sp3 = th.newTabSpec("tab3");
        sp3.setIndicator(getString(R.string.tab3));
        sp3.setContent(R.id.tab3);
        th.addTab(sp3);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
