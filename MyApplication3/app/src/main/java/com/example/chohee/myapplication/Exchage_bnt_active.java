package com.example.chohee.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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

    final static String strUrl = "http://finance.daum.net/exchange/exchangeMain.daum?DA=TMZ";//환율 정보를 가져올 url
    private final int MAX_SIZE_ARRAY_LIST = 43; //전체 환율 정보 길이
    private ArrayList<exchangeData> exchange_data_Arraylist; //환율 정보를 담을 arraylist
    private EditText ex_text, input_text; 
    private Spinner C_national_spinner_1, C_national_spinner_2;   //국가명을 담은 스피너 
    private TextView ex_result_1, ex_result_2;  //환산 결과를 보여줄 텍스트뷰

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) { //프레그먼트 이동시 호출.
        super.onViewCreated(view, savedInstanceState);
        exchange_data_Arraylist = new ArrayList<>();
        new getExchangeData().execute(strUrl);
        ex_text = (EditText) view.findViewById(R.id.ex_field);
        input_text = (EditText) view.findViewById(R.id.input_text);
        ex_result_1 = (TextView) view.findViewById(R.id.ex_result_1);
        ex_result_2 = (TextView) view.findViewById(R.id.ex_result_2);
        C_national_spinner_1 = (Spinner) view.findViewById(R.id.calculator_spinner_1);
        C_national_spinner_2 = (Spinner) view.findViewById(R.id.calculator_spinner_2);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.ex_calculator_1, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.ex_calculator_1, android.R.layout.simple_spinner_item); //스피너어뎁터에 아이템을 담는다. 
        C_national_spinner_1.setAdapter(adapter1);
        C_national_spinner_2.setAdapter(adapter2); //스피네에 어뎁터를 세팅한다.
        C_national_spinner_1.setSelection(1);//이벤트까지 같이 부른다. 미국으로 설정
        C_national_spinner_2.setSelection(0); //한국으로 설정
        C_national_spinner_1.setOnItemSelectedListener(new spinnerStateChange(1)); 		
        C_national_spinner_2.setOnItemSelectedListener(new spinnerStateChange(2)); //스피너를 이용해 국사를 선택했을 때 이벤트 리스너와 연결		
        input_text.addTextChangedListener(textWatcher); //textWatcher를 이용하여 edittext의 문자열의 변화를 보고 환율에 따른 결과값을 계산해서 출력한다.
	    ex_text.addTextChangedListener(textWatcher);
        input_text.setOnKeyListener(new keyStateChange()); //숫자값을 입력할 때 마다 키 리스너와 연결.
        ex_text.setOnKeyListener(new keyStateChange());
      
    }

    TextWatcher textWatcher =new TextWatcher() {  //edittext의 문자열이 바뀌는 것을 감시한다.
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {//텍스트가 바뀌기 전 실행되는 메서드
            if(input_text.hasFocus()) //input_text에 focus가 있을 경우 ex_text에 리스너를 제거한다
                ex_text.removeTextChangedListener(textWatcher);
            else if(ex_text.hasFocus())
                input_text.removeTextChangedListener(textWatcher);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { //텍스트가 바뀔때 실행되는 메서드
            if(input_text.hasFocus()) 
                calculatorEx_input_text(); //계산한다.
            else if(ex_text.hasFocus())
                calculatorEx_ex_text(); //환율 계산.
            Log.d("sequence",s.toString());

        }

        @Override
        public void afterTextChanged(Editable s) {  // 텍스트가 바뀌고 난 후 실행되는 메서드
            if(input_text.hasFocus())//input_text에 focus가 있을 경우 ex_text에 리스너를 붙인다.
                ex_text.addTextChangedListener(textWatcher);
            else if(ex_text.hasFocus())
                input_text.addTextChangedListener(textWatcher);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getClass().getName(), "oncreateView");
        return inflater.inflate(R.layout.exchage_bnt_active, container, false);
    }

    private void calculatorEx_input_text() { //환율 계산.
        try {
            int index1 = findArrayListIndex(1);
            int index2 = findArrayListIndex(2);
            Float result = changeEmptyString(input_text.getText().toString())
                    * (exchange_data_Arraylist.get(index2).getEx_rate().floatValue()
                    / exchange_data_Arraylist.get(index1).getEx_rate().floatValue());
            String result_float = String.format("%,.2f", result);
            String result_set=(result_float.substring(0, result_float.length() - 2).replace(",", "").replace(".", ""));
            Log.d("result", result_set);
            ex_text.setText(result_float);
            ex_result_2.setText("");
            ex_result_1.setText("");
            String input_format = String.format("%,.2f",changeEmptyString(input_text.getText().toString()));
            ex_result_1.append(result_float + exchange_data_Arraylist.get(index1).getK_ex());
            ex_result_2.append(input_format + exchange_data_Arraylist.get(index2).getK_ex());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (StackOverflowError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void calculatorEx_ex_text() { //환율 계산 메서드
        try {
            Log.d("start","c");
            int index1 = findArrayListIndex(1);
            int index2 = findArrayListIndex(2);

            Float result = changeEmptyString(ex_text.getText().toString())
                    * (exchange_data_Arraylist.get(index1).getEx_rate().floatValue()/exchange_data_Arraylist.get(index2).getEx_rate().floatValue());
            String result_float = String.format("%,.2f", result);
            input_text.setText(result_float);
            ex_result_2.setText("");
            ex_result_1.setText("");
            String input_format = String.format("%,.2f",changeEmptyString(ex_text.getText().toString()));
            ex_result_1.append(input_format + exchange_data_Arraylist.get(index1).getK_ex());
            ex_result_2.append(result_float + exchange_data_Arraylist.get(index2).getK_ex());

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (StackOverflowError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private StringBuffer downloadUrl(String myurl) {//url에 있는 html페이지를 읽어옴.
        StringBuffer Html = new StringBuffer(); //스트링 버퍼 선언
        String line = null;
        BufferedReader bufferedReader = null; //버퍼리더 선언
        BufferedInputStream buf = null;

        try {
            URL url = new URL(myurl); 
            HttpURLConnection conn1 = (HttpURLConnection) url.openConnection(); //url을 연결한다.
            if (conn1 != null) {
                conn1.setConnectTimeout(1000);  //1초 동안 url을 연결한다.
                conn1.setUseCaches(false); //캐쉬는 받지 않음.
                if (conn1.getResponseCode() == HttpURLConnection.HTTP_OK) { //http를 받았다면 실행.
                    buf = new BufferedInputStream(conn1.getInputStream()); // 연결한 url의 스트림을 받는다
                    bufferedReader = new BufferedReader(new InputStreamReader(buf, "euc-kr")); //한국어로 받기 위해 euc-kr로 !bufferedReader로 받는다
                    while (true) {  //bufferedReader가 null이 아닐때까지 읽어온다.
                        line = bufferedReader.readLine();
                        if (line == null) break;
                        Html.append(line + "\n"); //Html에 append
                    }
                }
                bufferedReader.close(); 
            }
            conn1.disconnect();
            return Html;//반환
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int findArrayListIndex(int sel) { //인덱스 값을 반환하는 함수,  
        int cnt = 0;
        switch (sel) {
            case 1:
                for (exchangeData d : exchange_data_Arraylist) {
                    if (Integer.valueOf(d.getId())==C_national_spinner_1.getSelectedItemPosition()) {//스피너에 선택된 국가의 이름과 동일한 index 값을 찾아 반환 한다.
                        cnt = Integer.valueOf(d.getId());
                        Log.d("c_1",d.getId());
                        break;
                    }
                }
                break;
            case 2:
                for (exchangeData d : exchange_data_Arraylist) {
                    if (Integer.valueOf(d.getId())==C_national_spinner_2.getSelectedItemPosition()) { //스피너에 선택된 국가의 이름과 동일한 index 값을 찾아 반환 한다.
                        cnt = Integer.valueOf(d.getId());
                        Log.d("c_2",d.getId());
                        break;
                    }
                }
                break;
        }
        return cnt;
    }

    public Float changeEmptyString(String str) {  //String의 empty일 경우 처리하는 메서드.
        Float rint = null;
        try {
            if (str.equals("")) {
                rint = Float.valueOf(0); //0으로 바꿔준다. 
            } else {
                if (str.contains(".") || str.contains(",")) {  //이미 format값이 있는 경우 계산을 위해 format값을 없애준다.
                    //rint = Float.valueOf(str.substring(0, str.length() - 2).replace(",", "").replace(".", ""));
					rint = Float.valueOf(str.replace(",", "").replace(".", ""));
                } else {
                    rint = Float.valueOf(str);// String을 Float값으로 변환.
                }
            }
            return rint;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return rint;
    }

    private class getExchangeData extends AsyncTask<String, Void, StringBuffer> {
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

		
		//이 메서드들은 백그라운드 실행 매서드로 UI를 직접적으로 접근할 수 없다.
        @Override
        protected StringBuffer doInBackground(String... params) {  //스레드 실행시 바로 실행되는 메서드.
            try {
                StringBuffer page = downloadUrl(strUrl);
                Log.d("AsyncTask", "end");
                onPostExcute(page); //다음 실행 메서드 

            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExcute(StringBuffer result) { //읽어온 Html 내용을 파싱한다. 

            String word = "";
            int pt_start = -1;
            int pt_end = -1;
            String tag_start = "ex[0] = 'KRW';ex_rate[0] = \"1\";country[0] = '대한민국'; k_ex[0] = '원';full_k_ex[0] = '원';"; //처음 내용
            String tag_end = "    \t// 데이타 Setting   ---------------------------------"; //마지막 내용.

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
                        exdata.display();
                        Log.d("size",String.valueOf(exchange_data_Arraylist.size()));
                    }
                }
            } else {
                Log.d("fail,,,", "fail...");
            }
        }
    }

    private class keyStateChange implements View.OnKeyListener {  //키 리스너 클래스.
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            final boolean isEnterEvent = event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER; //이벤트가 널이 아니고 엔터를 입력했을 경우.
            switch (v.getId()) {
                case R.id.input_text: {
                    try {
                        if (isEnterEvent) {
                            calculatorEx_input_text();
                        }
                        break;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                case R.id.ex_field: {
                    Log.d("input_ex", String.valueOf(isEnterEvent));
                    try {
                        if (isEnterEvent) {
                          calculatorEx_ex_text();
                        }
                        break;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }
    }

    private class spinnerStateChange implements AdapterView.OnItemSelectedListener { //스피너가 바뀔 때마다 선택한 나라에 환율 값으로 변환.

        private int flag;

        public spinnerStateChange(int flag){//생성자로 변수를 지정해서 스퍼너가 선택될 때마다 구분이 가능하도록 한다.
            this.flag=flag;
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if ((input_text.getText().toString().length() != 0 || ex_text.getText().toString().length() != 0) && exchange_data_Arraylist.size() == MAX_SIZE_ARRAY_LIST) { //사용자가 아이템을 선택하고 input 텍스트가 비어있지 않다면 환율을 계산한다.
               if(flag==2){
                   calculatorEx_input_text();
                   input_text.requestFocus();
               }
                else if(flag==1){
                   ex_text.requestFocus();
                   calculatorEx_ex_text();
               }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    private class exchangeData {  //나라마다 환율 정보를 담을 클래스.
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
