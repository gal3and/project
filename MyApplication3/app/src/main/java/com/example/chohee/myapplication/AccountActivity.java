package com.example.chohee.myapplication;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by chohee on 2016-03-14.
 */
public class AccountActivity extends android.support.v4.app.Fragment implements View.OnClickListener,FragmentManager.OnBackStackChangedListener{

    @Override
    public void onResume() {
        Log.d(getClass().getName(),"ONresume");
        super.onResume();
    }

    @Override
    public void onDetach() {
        Log.d(getClass().getName(), "ONDetach");
        super.onDetach();
    }

    @Override
    public void onStart() {
        Log.d(getClass().getName(),"ONDEStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(getClass().getName(),"ONDESTop");
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {

        Log.d(getClass().getName(),"ONattach");
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Log.d(getClass().getName(), "ONviewcreate");
        super.onViewCreated(view, savedInstanceState);
        final Bundle state = savedInstanceState;

        TextView b1 = (TextView) view.findViewById(R.id.account_button);
        //Button b2 = (Button) view.findViewById(R.id.exchange_button);

        b1.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      if (state == null) {

                                          FragmentManager fragmentManager=getFragmentManager();
                                          FragmentTransaction transaction=fragmentManager.beginTransaction();
                                          transaction.replace(R.id.account_fragment,new Account_bnt_active()).commit();
                                          fragmentManager.popBackStack();
                                      }
                                  }
                              }
        );

    }








    @Override
    public void onDestroy() {
        Log.d(getClass().getName(),"ONDESTROY");
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d(getClass().getName(),"oncreateView");
        return inflater.inflate(R.layout.accountlayout,container,false);
    }


    public void onClick(View v) {

    }

    @Override
    public void onBackStackChanged() {

    }
}

