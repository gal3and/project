package com.example.com.ppopuptest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    //Button btn;
    EditText input;
    TextView txt;
    String strAmount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //to do list
        //1.팝업 띄우기 전에 에딧 텍스트는 단순 텍스트 뷰로 변환 -ok
        //2.입력한 값이 텍스트 뷰에 설정되도록 함 -ok
        //3.입력창에 예전 입력했던 값 삭제되도록 함 -ok
        //4.입력창을 숫자만 입력되도록 함-ok
        //5.돈 단위 표시 되도록 함-ok http://ggari.tistory.com/8
        //6.박스 표시 -ok http://cmdream.tistory.com/1

        txt = (TextView)findViewById(R.id.textView);
        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(strAmount)) { // StackOverflow를 막기위해,
                    strAmount = makeStringComma(s.toString().replace(",", ""));
                    input.setText(strAmount);
                    Editable e = input.getText();
                    Selection.setSelection(e, strAmount.length());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
            protected String makeStringComma(String str) {
                if (str.length() == 0)
                    return "";
                long value = Long.parseLong(str);
                DecimalFormat format = new DecimalFormat("###,###");
                return format.format(value);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onClick1(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("예산 입력");
        alert.setMessage("예산을 입력하세요.");

        alert.setView(input);

        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String inputvalue = input.getText().toString();
                txt.setText(inputvalue);
                input.setText(null);
                ((ViewGroup)input.getParent()).removeView(input);
            }
        });


        alert.setNegativeButton("no",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                input.setText(null);
                ((ViewGroup)input.getParent()).removeView(input);
            }
        });
        alert.show();
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
