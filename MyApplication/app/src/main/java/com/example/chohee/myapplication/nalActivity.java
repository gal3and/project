package com.example.chohee.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class nalActivity extends Fragment{
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

       super.onViewCreated(view, savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nallayout,container,false);
    }

}
