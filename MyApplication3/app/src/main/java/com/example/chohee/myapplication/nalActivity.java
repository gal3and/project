package com.example.chohee.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by chohee on 2016-03-14.
 */
public class nalActivity extends android.support.v4.app.Fragment{
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

       super.onViewCreated(view, savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nallayout,container,false);
    }

}
