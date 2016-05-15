package com.example.chohee.myapplication;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import java.util.regex.Pattern;


public class SettingActivity extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    private SharedPreferences pref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref=getPreferenceManager().getSharedPreferences();
        pref.registerOnSharedPreferenceChangeListener(this);
        addPreferencesFromResource(R.xml.preference);

        findPreference("email_address").setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                      if(Pattern.matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}",(String)newValue)){
                          return true;
                      }else{
                          Toast.makeText(getActivity(),"다시입력해주세요",Toast.LENGTH_SHORT);
                          return false;
                      }
                    }
                }
        );
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals("email_address")){
            Preference preference=findPreference(key);
            preference.setSummary(pref.getString(key,""));
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }



}
