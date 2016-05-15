package com.example.chohee.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yoon on 2016-05-11.
 */
public class AccountItemDialog extends DialogFragment {

        private Spinner item_spinner;
        private EditText input_price;
        private DatePicker datePicker;
        private String [] item_arr={"음식","쇼핑","기념품","기타"};
        private ArrayAdapter<String> adapter;
        Calendar cal= Calendar.getInstance();
        private OnsetItem mylistener;

       public static interface OnsetItem{
           void returnResult(AccountItemDto data);
       }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mylistener=(OnsetItem) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnsetItem");
        }
    }


    @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater =LayoutInflater.from(getActivity());
            RelativeLayout root=(RelativeLayout) inflater.inflate(R.layout.dialog_account_item,null);

            datePicker=(DatePicker) root.findViewById(R.id.datePicker);
            input_price=(EditText) root.findViewById(R.id.item_price_field);
            item_spinner=(Spinner) root.findViewById(R.id.item_spinner);
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,item_arr);
            item_spinner.setAdapter(adapter);
            item_spinner.setPrompt("선택");
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                }
            });

            AlertDialog.Builder b=new AlertDialog.Builder(getActivity());
            b.setView(root);
            b.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String price=input_price.getText().toString();
                    if(!price.equals("")){
                        AccountItemDto data=new AccountItemDto();
                        data.setItem_price(Integer.valueOf(price));
                        data.setItem_name(item_spinner.getSelectedItem().toString());
                        int month=datePicker.getMonth()+1;
                        data.setBuy_date(datePicker.getYear()+"년"+month+"월"+datePicker.getDayOfMonth()+"일");
                        DBAdapter dbAdapter=new DBAdapter(getActivity());
                        dbAdapter.insert_account(data.getBuy_date(),data.getItem_name(),data.getItem_price());
                        mylistener.returnResult(data);
                    }
                }
            });
            b.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            });
            return b.create();
        }


    }