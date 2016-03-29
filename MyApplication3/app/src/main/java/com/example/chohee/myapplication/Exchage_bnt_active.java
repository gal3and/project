package com.example.chohee.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 * Created by chohee on 2016-03-26.
 */
public class Exchage_bnt_active extends android.support.v4.app.Fragment {

    final static String strUrl = "http://finance.daum.net/exchange/exchangeMain.daum?DA=TMZ";
    private ArrayList<exchageData> exchange_data_Arraylist;
    private EditText ex_text, input_text;
    private Spinner C_national_spinner_1, C_national_spinner_2;
    private TextView ex_result_1, ex_result_2;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exchange_data_Arraylist = new ArrayList<>();
        new getExchageData().execute(strUrl);

        ex_text = (EditText) view.findViewById(R.id.ex_field);
        input_text = (EditText) view.findViewById(R.id.input_text);
        ex_result_1 = (TextView) view.findViewById(R.id.ex_result_1);
        ex_result_2 = (TextView) view.findViewById(R.id.ex_result_2);
        ex_text.setClickable(false);
        C_national_spinner_1 = (Spinner) view.findViewById(R.id.calculator_spinner_1);
        C_national_spinner_2 = (Spinner) view.findViewById(R.id.calculator_spinner_2);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.ex_calculator_1, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.ex_calculator_1, android.R.layout.simple_spinner_item);
        C_national_spinner_1.setAdapter(adapter1);
        C_national_spinner_2.setAdapter(adapter2);
       // C_national_spinner_1.setSelection(1);//이벤트까지 같이 부른다.   -> 다시 생각해보자..
     //   C_national_spinner_2.setSelection(0);
        C_national_spinner_1.setOnItemSelectedListener(new chageState());
        C_national_spinner_2.setOnItemSelectedListener(new chageState());
        input_text.setOnKeyListener(new chageState());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getClass().getName(), "oncreateView");
        return inflater.inflate(R.layout.exchage_bnt_active, container, false);
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

    private int findArrayLinstIndex(int sel) {
        int cnt = 0;
        switch (sel) {
            case 1:
                for (exchageData d : exchange_data_Arraylist) {
                    if (d.getCountry().equals(C_national_spinner_1.getSelectedItem().toString())) {
                        cnt = Integer.valueOf(d.getId());
                        break;
                    }
                }
                break;
            case 2:
                for (exchageData d : exchange_data_Arraylist) {
                    if (d.getCountry().equals(C_national_spinner_2.getSelectedItem().toString())) {
                        cnt = Integer.valueOf(d.getId());
                        break;
                    }
                }
                break;
        }
        return cnt;
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
                    exchageData exdata = new exchageData();

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

    private class chageState implements AdapterView.OnItemSelectedListener, View.OnKeyListener{

        public int chageEmptyString(String str){
            int rint=0;
            if(str.equals("")) {
                rint = 0;
            }
            else{
                rint=Integer.valueOf(str);
            }
            return rint;
        }

        @Override
        public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
               if (!input_text.getText().equals("")) {
                    int index1 = findArrayLinstIndex(1);
                    int index2 = findArrayLinstIndex(2);
                    float result = chageEmptyString(input_text.getText().toString())
                            * ((exchange_data_Arraylist.get(index2).getEx_rate().floatValue() / exchange_data_Arraylist.get(index2).getRate_won())
                            / (exchange_data_Arraylist.get(index1).getEx_rate().floatValue() / exchange_data_Arraylist.get(index1).getRate_won()));
                    String result_float = String.format("%,.2f", result);
                    ex_text.setText(result_float);
                    ex_result_2.setText("");
                    ex_result_1.setText("");
                    ex_result_1.append(result_float + exchange_data_Arraylist.get(index1).getK_ex());
                    ex_result_2.append(input_text.getText() + exchange_data_Arraylist.get(index2).getK_ex());
                }else{
                   ex_result_2.setText("");
                   ex_result_1.setText("");
               }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                int index1 = findArrayLinstIndex(1);
                int index2 = findArrayLinstIndex(2);
                Log.d("keydown", exchange_data_Arraylist.get(index1).getCountry());
                float result = chageEmptyString(input_text.getText().toString())
                        * ((exchange_data_Arraylist.get(index2).getEx_rate().floatValue() / exchange_data_Arraylist.get(index2).getRate_won())
                        / (exchange_data_Arraylist.get(index1).getEx_rate().floatValue() / exchange_data_Arraylist.get(index1).getRate_won()));
                String result_float = String.format("%,.2f", result);
                ex_text.setText(result_float);
                ex_result_2.setText("");
                ex_result_1.setText("");
                ex_result_1.append(result_float + exchange_data_Arraylist.get(index1).getK_ex());
                ex_result_2.append(input_text.getText() + exchange_data_Arraylist.get(index2).getK_ex());

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if(ex_text.getText().equals("")){

                    }
                }
            }
            return false;
        }

    }

    private class exchageData {
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

        public void display() {
            Log.d("exdata", getCountry() + " " + getEx_rate() + " " + getEx() + " " + getId() + " " + getRate_won() + " " + getEx_full() + " " + getK_ex());
        }
    }
}
