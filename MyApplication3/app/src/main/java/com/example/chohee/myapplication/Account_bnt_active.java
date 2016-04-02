package com.example.chohee.myapplication;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.inputmethodservice.InputMethodService.InputMethodImpl;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.text.TextWatcher;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Account_bnt_active extends android.support.v4.app.Fragment implements OnClickListener,AdapterView.OnItemClickListener {


    final static String strUrl = "http://finance.daum.net/exchange/exchangeMain.daum?DA=TMZ";  //정보를 가져올 url
    InputMethodManager imm;
    private ArrayList<exchangeData> exchange_data_Arraylist;
    private TextView start_bnt, end_bnt;
    TextView Array_item;
    private Spinner national_spinner, item_spinner;
    private EditText budget_input;
    private TextView budget_txt;
    private String strAmount = "";
    private TextView Text1, Datebtn1, Maintext, spend_bnt1;
    private EditText Edit1;
    private View dialog1View, calView;
    private DatePicker Picker1;
    private ArrayAdapter<String> adapter;
    private float Contry_rate;
    private ArrayList<String> buy_Item_array = null;
    private TableLayout table;
    private TableRow tr;
    private ArrayAdapter<String> buy_Item_Adater;
    private ListView buy_item_list_view;
    static String output = "";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Array_item=new TextView(getContext());
        national_spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.national, android.R.layout.simple_spinner_dropdown_item);
        national_spinner.setAdapter(adapter);
        start_bnt = (TextView) view.findViewById(R.id.start_button);
        end_bnt = (TextView) view.findViewById(R.id.end_button);
        exchange_data_Arraylist = new ArrayList<>();
        national_spinner.setSelection(0);
        budget_txt = (TextView) view.findViewById(R.id.budget);
        budget_input = (EditText) view.findViewById(R.id.budget_text_filed);
        item_spinner = (Spinner) view.findViewById(R.id.buy_item_spinner);
        Text1 = (TextView) view.findViewById(R.id.text1);
        Maintext = (TextView) view.findViewById(R.id.maintext);
        spend_bnt1 = (TextView) view.findViewById(R.id.spend_btn1);
        spend_bnt1.setOnClickListener(this);
        buy_Item_array = new ArrayList<String>();
        table = (TableLayout) view.findViewById(R.id.budget_table);
        tr = new TableRow(getActivity());
        buy_Item_Adater = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, buy_Item_array);
        buy_item_list_view = (ListView) view.findViewById(R.id.buy_item_list_view);
        buy_item_list_view.setAdapter(buy_Item_Adater);
        //  buy_item_list_view.setOnItemClickListener(this);

        buy_item_list_view.setOnItemClickListener(this);

        budget_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(strAmount)) { // StackOverflow를 막기위해,
                    strAmount = makeStringComma(s.toString().replace(",", ""));
                    budget_input.setText(strAmount);
                    Editable e = budget_input.getText();
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

        start_bnt.setOnClickListener(this);
        end_bnt.setOnClickListener(this);


    }

    public void onClick1(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("예산 입력");
        alert.setMessage("예산을 입력하세요.");

        alert.setView(budget_input);

        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String inputvalue = budget_input.getText().toString();
                budget_txt.setText(inputvalue);
                budget_txt.setText(null);
                ((ViewGroup) budget_input.getParent()).removeView(budget_input);
            }
        });


        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                budget_input.setText(null);
                ((ViewGroup) budget_input.getParent()).removeView(budget_input);
            }
        });
        alert.show();
    }


    private StringBuffer downloadUrl(String myurl) {
        Log.d(getClass().getName(), "oncreateView");
        StringBuffer Html = new StringBuffer();
        String line = null;
        String page = "";
        BufferedReader bufferedReader = null;
        BufferedInputStream buf = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
            if (conn1 != null) {
                conn1.setConnectTimeout(1000);
                conn1.setUseCaches(false);
                if (conn1.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    buf = new BufferedInputStream(conn1.getInputStream());
                    bufferedReader = new BufferedReader(new InputStreamReader(buf, "euc-kr"));
                    while (true) {
                        line = bufferedReader.readLine();
                        if (line == null) break;
                        Html.append(line + "\n");
                    }
                }
                bufferedReader.close();
            }
            conn1.disconnect();
            return Html;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getClass().getName(), "oncreateView");
        return inflater.inflate(R.layout.account_btn_active, container, false);
    }


    @Override
    public void onClick(View v) {
        exchangeData selectexData = new exchangeData();

        switch (v.getId()) {

            case R.id.start_button:
                new getExchageData().execute(strUrl);//백그라운드 스레드라서 처리시간이 걸린다... 어케하징..

                selectexData = findArrayListIndex(selectexData);

                Log.d("click", selectexData.getCountry() + " " + String.valueOf(selectexData.getEx_rate()));

                break;
            case R.id.end_button:
                new getExchageData().execute(strUrl);
                break;
            case R.id.spend_btn1:
                inputItem();
                break;
        }
    }


    private void inputItem() {
        createDialog();
        Log.d("buy", Array_item.getText().toString());
        buy_Item_array.add(Array_item.getText().toString());
        buy_Item_Adater.notifyDataSetChanged();
        Array_item.setText("");
    }

    private void createDialog() {

        final String[] index = {"쇼핑", "음식", "기념품", "기타"};
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, index);

        try {
            dialog1View = (View) View.inflate(getActivity(), R.layout.dialog, null); //인플레이트해서 대입
            AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());//모든메소드의 반환값
            dlg.setTitle("지출내역을 입력해주세요");


            dlg.setView(dialog1View);
            Datebtn1 = (TextView) dialog1View.findViewById(R.id.datebtn1);

            Datebtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    calView = (View) View.inflate(getActivity(), R.layout.calender, null); //인플레이트해서 대입
                    AlertDialog.Builder dlg1 = new AlertDialog.Builder(getActivity());//모든메소드의 반환값
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
                                    Array_item.append(re);
                                }
                            });
                    dlg1.setNegativeButton("취소", null);
                    dlg1.show();
                }
            });

            Edit1 = (EditText) dialog1View.findViewById(R.id.input_item_price_field);
            item_spinner = (Spinner) dialog1View.findViewById(R.id.buy_item_spinner);
            item_spinner.setPrompt("선택");
            item_spinner.setAdapter(adapter);

            item_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String se = String.format(index[arg2].toString());
                    Array_item.append("\t" + se);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            dlg.setPositiveButton("입력",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Array_item.append( "\t" + Edit1.getText().toString());
                        }
                    });
            dlg.setNegativeButton("취소", null);
            dlg.show();

            Log.d("select", Array_item.getText().toString()+"dkdkdk");


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private exchangeData findArrayListIndex(exchangeData Find_data) {

        for (exchangeData d : exchange_data_Arraylist) {
            Log.d("push", national_spinner.getSelectedItem().toString());

            if (d.getCountry().equals(national_spinner.getSelectedItem().toString())) {
                Find_data.setEx(d.getEx());
                Find_data.setId(d.getId());
                Find_data.setRate_won(d.getRate_won());
                Find_data.setEx_full(d.getEx_full());
                Find_data.setK_ex(d.getK_ex());
                Find_data.setCountry(d.getCountry());
                Find_data.setEx_rate(d.getEx_rate());
                break;
            }
        }
        return Find_data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {

        String data =buy_Item_array.get(position);

        String message ="삭제 또는 수정을 선택하세요.";

        new AlertDialog.Builder(getActivity())
                .setTitle("삭제/수정")
                .setMessage(message)
                .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createDialog();
                        Log.d("buy", Array_item.getText().toString());
                        buy_Item_array.add(position,Array_item.getText().toString());
                        buy_Item_Adater.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buy_Item_array.remove(position);
                        buy_Item_Adater.notifyDataSetChanged();
                    }
                })
                .show();
    }

    private class dialogListener{
        String item;

        public dialogListener() {
            this.item = "";
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }


    }

    private class getExchageData extends AsyncTask<String, Void, StringBuffer> {
        /*
        * 메인 스레드와 다르게 백그라운드에서 돌아가는 스레드
        * AsyncTask<Params,Progress,Result>
        *     Params 백그라운드작업시 필요한 데이퍼의 type 지정
        *     Progress 백그라운드 작업 중 진행 사항을 표현하는데 사용되는 data를 위한 type 지정.
        *     Result 백그라운드 작업 완료 후 리턴 할 data의 type 지정.
        *
        * 오버라이딩 함수
        * AsyncTask:execute(Params)
        * Result AsyncTask:doInBackground(Params)
        * AsyncTask:publishProgress(Progress)
        * AsyncTask:onProgressUpdate(Progress)
        * AsyncTask:onPostExecute(result)
        *
        *protected void onPreExecute(): Background 작업이 시작되자마자
        * UI스레드에서 실행될 코드를 구현해야 함.
        * (예. background 작업의 시작을 알리는 text표현, background 작업을 위한 ProgressBar popup등)
        protected abstract Result doInBackground(Params… params): Background에서 수행할 작업을 구현해야 함. execute(…) 메소드에 입력된 인자들을 전달 받음.
        void onProgressUpdate(Progress... values): publishProgress(…) 메소드 호출의 callback으로 UI스레드에서 보여지는 background 작업 진행 상황을 update하도록 구현함. (예. ProgressBar 증가 등)
        void onPostExecute(Result result): doInBackground(…)가 리턴하는 값을 바탕으로 UI스레드에 background 작업 결과를 표현하도록 구현 함.
         (예. background작업을 계산한 복잡한 산술식에 대한 답을 UI 위젯에 표현함 등)
        void onCancelled(): AsyncTask:cancel(Boolean) 메소드를 사용해 AsyncTask인스턴스의 background작업을 정지 또는 실행금지 시켰을 때 실행되는 callback.
        background작업의 실행정지에 따른 리소스복구/정리 등이 구현될 수 있다.      *
        *
        * */

        @Override
        protected StringBuffer doInBackground(String... params) {
            try {
                StringBuffer page = downloadUrl(strUrl);
                Log.d("1", "end");
                onPostExcute(page);

            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExcute(StringBuffer result) {

            String word = "";
            int pt_start = -1;
            int pt_end = -1;
            String tag_start = "ex[0] = 'KRW';ex_rate[0] = \"1\";country[0] = '대한민국'; k_ex[0] = '원';full_k_ex[0] = '원';";
            String tag_end = "    \t// 데이타 Setting   ---------------------------------";

            pt_start = result.indexOf(tag_start);
            pt_end = result.lastIndexOf(tag_end);


            if (pt_start != -1) {
                /*
                var ex = new Array();           // 단위         - CODE
                var k_ex = new Array();         // 한글단위     -
            	var full_k_ex = new Array();    // 한글 풀네임  - CODE_NAME
    	        var ex_rate = new Array();    //  rate
    	        var country = new Array();    // 한글 풀네임  - CODE_NAME
                * */
                String substringdata = result.substring(pt_start, pt_end);
                StringTokenizer stringTokenizer = new StringTokenizer(substringdata, "\n");
                String line;
                while (stringTokenizer.hasMoreTokens()) {
                    line = stringTokenizer.nextToken().trim().replace("\"", "").replace(" ", "");
                    StringTokenizer Token = new StringTokenizer(line, "[]=_;'");
                    exchangeData exdata = new exchangeData();

                    while (Token.hasMoreElements()) {

                        String parsing_data = Token.nextToken().trim();


                        if (parsing_data.contains("ex")) {
                            parsing_data = Token.nextToken().trim();
                            if (parsing_data.contains("rate")) {
                                exdata.setId(Token.nextToken().trim());
                                exdata.setEx_rate(Float.parseFloat(Token.nextToken().trim()));
                                //  Log.d("ex_rate", String.valueOf(exdata.ex_rate));
                            } else {
                                exdata.setId(parsing_data);
                                //  Log.d("ex.id",exdata.id);
                                exdata.setEx(Token.nextToken().trim());
                                //  Log.d("ex", exdata.ex);
                            }
                        }
                        if (parsing_data.contains("country")) {
                            exdata.setId(Token.nextToken().trim());
                            exdata.setCountry(Token.nextToken().trim());
                            //Log.d("country", exdata.country);
                            if (exdata.getCountry().equals("일본") || exdata.getCountry().equals("베트남") || exdata.getCountry().equals("인도네시아")) {
                                exdata.setRate_won(100);
                            } else {
                                exdata.setRate_won(1);
                            }
                        }

                        if (parsing_data.contains("k")) {
                            parsing_data = Token.nextToken().trim();
                            if (parsing_data.contains("ex")) {
                                exdata.setId(Token.nextToken().trim());
                                exdata.setK_ex(Token.nextToken().trim());
                                // Log.d("K_ex", exdata.K_ex);
                            }
                        }


                        if (parsing_data.contains("full")) {
                            parsing_data = Token.nextToken().trim();
                            if (parsing_data.contains("k")) {
                                parsing_data = Token.nextToken().trim();
                                if (parsing_data.contains("ex")) {
                                    exdata.setId(Token.nextToken().trim());
                                    exdata.setEx_full(Token.nextToken().trim());
                                    //   Log.d("ex_full", exdata.ex_full);
                                }
                            }
                        }
                    }

                    if (exdata.getCountry() != null) {
                        exchange_data_Arraylist.add(exdata);
                    }
                }
            } else {
                Log.d("fail,,,", "fail...");
            }
        }
    }

    private class exchangeData {
        String country;//나라
        Float ex_rate;//환율
        String K_ex;//한국어 단위
        String ex;//영어 단위
        int rate_won; // 1000원 당 단위
        String ex_full;
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEx_full() {
            return ex_full;
        }

        public void setEx_full(String ex_full) {
            this.ex_full = ex_full;
        }

        public String getEx() {
            return ex;
        }

        public void setEx(String ex) {
            this.ex = ex;
        }

        public int getRate_won() {
            return rate_won;
        }

        public void setRate_won(int rate_won) {
            this.rate_won = rate_won;
        }

        public String getK_ex() {
            return K_ex;
        }

        public void setK_ex(String k_ex) {
            K_ex = k_ex;
        }

        public Float getEx_rate() {
            return ex_rate;
        }

        public void setEx_rate(Float ex_rate) {
            this.ex_rate = ex_rate;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }


    }

    private class myAccount_item {
        String item_Titel;//품목
        int charge;//가격
        Long charge_won;//한국 가격
    }

}