package com.example.min.moneytest2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView Text1, Datebtn1;
    Button Btn1;
    EditText Edit1, Edit2, Edit3;
    Spinner Spinner1;
    View dialog1View, calView;
    DatePicker Picker1;

    ArrayAdapter<String> adapter;
    final String[] index = {"선택", "쇼핑", "음식", "기념품", "기타"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("지출입력하기");

        Text1 = (TextView) findViewById(R.id.text1);

        Btn1 = (Button) findViewById(R.id.btn1);
        Edit2 = (EditText) findViewById(R.id.edit2);
        Edit3 = (EditText) findViewById(R.id.edit3);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, index);

        //지출내역 입력을 클릭했을경우
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1View = (View) View.inflate(MainActivity.this, R.layout.dialog1, null); //인플레이트해서 대입
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);//모든메소드의 반환값
                dlg.setTitle("지출내역을 입력해주세요");
                //dlg.setIcon(R.drawable.dlgicon);

                dlg.setView(dialog1View);
                Datebtn1 = (TextView) dialog1View.findViewById(R.id.datebtn1);

                Spinner1 = (Spinner) dialog1View.findViewById(R.id.spinner1);
                Spinner1.setAdapter(adapter);

                Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        String se = String.format(index[arg2].toString());
                        Edit3.setText(String.format(se));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                Edit1 = (EditText) dialog1View.findViewById(R.id.edit1);

                Datebtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calView = (View) View.inflate(MainActivity.this, R.layout.cal, null); //인플레이트해서 대입
                        AlertDialog.Builder dlg1 = new AlertDialog.Builder(MainActivity.this);//모든메소드의 반환값
                        dlg1.setTitle("날짜를 입력해주세요");
                        dlg1.setView(calView);

                        Picker1 = (DatePicker) calView.findViewById(R.id.picker1);
                        Picker1.init(Picker1.getYear(), Picker1.getMonth(), Picker1.getDayOfMonth(),//오늘날짜로초기화진행
                                new DatePicker.OnDateChangedListener() { //값이 바뀔때마다 텍스트 바뀜을 적용
                                    @Override
                                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        Datebtn1.setText(String.format("%d년 %d 월 %d일", year, monthOfYear + 1, dayOfMonth));
                                    }
                                });

                        dlg1.setPositiveButton("입력",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) { //입력클릭을 동작하는곳
                                        String re = String.format("%d년 %d월 %d일", Picker1.getYear(), Picker1.getMonth() + 1, Picker1.getDayOfMonth());
                                        Edit2.setText(String.format(re));
                                    }
                                });
                        dlg1.setNegativeButton("취소", null);
                        dlg1.show();
                    }
                });

                dlg.setPositiveButton("입력",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Edit3.append(Edit1.getText().toString());

                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        Edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"클릭",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
