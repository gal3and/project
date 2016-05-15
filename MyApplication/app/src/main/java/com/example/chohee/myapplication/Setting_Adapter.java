package com.example.chohee.myapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by USER1 on 2016-05-12.
 */
public class Setting_Adapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private static final int ITEM_VIEW_TYPE_SET_NATION=0;
    private static final int ITEM_VIEW_TYPE_SET_EMAIL=1;
    private static final int ITEM_VIEW_TYPE_SET_NETWORK=2;
    private static final int ITEM_VIEW_TYPE_MAX=3;
    private Spinner nation,email_spinner;
    private SwitchCompat gps_active,email_active,wifi_active;
    private EditText email_addr,self_email_domain;

    private String email_domain[]={"@naver.com","@hanmail.com","@gmail.com","직접입력"};

    public ArrayList<Setting_ListData> setting_listDatas=new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        return setting_listDatas.get(position).getType();
    }


    @Override
    public int getCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    @Override
    public Object getItem(int position) {
        return setting_listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context=parent.getContext();
        int viewType=getItemViewType(position);

        if(convertView==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Setting_ListData data=setting_listDatas.get(position);

            switch (viewType){
                case ITEM_VIEW_TYPE_SET_NATION:
                   convertView=inflater.inflate(R.layout.setting_nation,parent,false);
                    nation = (Spinner) convertView.findViewById(R.id.setting_nation);
                    gps_active=(SwitchCompat) convertView.findViewById(R.id.active_gps);
                    ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.national, android.R.layout.simple_spinner_dropdown_item);
                    nation.setAdapter(adapter);
                    break;
                case ITEM_VIEW_TYPE_SET_EMAIL :
                    convertView=inflater.inflate(R.layout.setting_email,parent,false);
                    email_active=(SwitchCompat)convertView.findViewById(R.id.active_email);
                    email_addr=(EditText) convertView.findViewById(R.id.email_id);
                    email_spinner=(Spinner) convertView.findViewById(R.id.email_domain);
                    ArrayAdapter adapter1 = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line,email_domain);
                    email_spinner.setAdapter(adapter1);
                    self_email_domain=(EditText) convertView.findViewById(R.id.self_email_domain);
                    email_active.setOnCheckedChangeListener(this);
                    break;
                case ITEM_VIEW_TYPE_SET_NETWORK :
                    convertView=inflater.inflate(R.layout.setting_network,parent,false);
                    wifi_active = (SwitchCompat) convertView.findViewById(R.id.active_wifi);
                    break;
            }
        }
        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id=buttonView.getId();

        if(id==R.id.active_email){
            if(isChecked){
                self_email_domain.setClickable(true);
            }
        }
    }

    public void addItem(int type){
       Setting_ListData data=new Setting_ListData();
        data.setType(type);
        setting_listDatas.add(data);
    }

    class Setting_ListData{
        private int type;
        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }
    }
}
