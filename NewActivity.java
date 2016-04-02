package org.tacademy.mytab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {

    private Button b1;
    private Button b2;
    private Button b3;

    private Number totalMoney = 5000000; // 총 보유 액
    private Number useMoney = 2000; // 쓴돈
    private Number exchange = 1200;

    //private String mail, subject, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        b1 = (Button) findViewById(R.id.oneButton); //쓴돈
        b2 = (Button) findViewById(R.id.twoButton); //남은돈
        b3 = (Button) findViewById(R.id.threeButton); //한화남은돈

        Number leftMoney = totalMoney.intValue() - useMoney.intValue();//남은돈
        Number exMoney = leftMoney.intValue() * exchange.intValue();

        b1.setText(b1.getText() + useMoney.toString());
        b2.setText(b2.getText() + leftMoney.toString());
        b3.setText(b3.getText() + exMoney.toString());

    }

    public void onBackButtonClicked(View v) {
        Toast.makeText(getApplicationContext(), "메인으로 돌아갑니다.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onCalButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), T1.class);
        startActivity(intent);
    }

    public void onOutButtonClicked(View v) {

/*
        Intent it = new Intent(Intent.ACTION_SEND);
        String[] mailaddr = {"sungwg1988@naver.com"};
        it.setType("plaine/text");
        it.putExtra(Intent.EXTRA_EMAIL, mailaddr); // 받는사람
        it.putExtra(Intent.EXTRA_SUBJECT, "[wisay]"); // 제목
        it.putExtra(Intent.EXTRA_TEXT, "\n\n" + "v" + //appVersion
        "asdasd"); // 첨부내용
        startActivity(it);
*/
/*
        mail = "sungwg1988@naver.com";
        subject = "test";
        body = "asd";

        //Intent send = new Intent(Intent.ACTION_SENDTO);
        Intent send = new Intent(Intent.ACTION_SEND);
        String uriText = "mailto:" + Uri.encode(mail) +
                "?subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(body);
        Uri uri = Uri.parse(uriText);
        send.setData(uri);
        //context.
        startActivity(Intent.createChooser(send, "Send mail..."));


        //mail, subject, body는 String 타입
        //mail = user@gmail.com 유형
        //        subject = 제목 유형
        //        body = 본문 내용 유형

*/

/*
        // 이메일 발송

        Uri uri = Uri.parse("mailto:sungwg1988@naver.com");

        Intent it = new Intent(Intent.ACTION_SENDTO, uri);

        startActivity(it);
*/

//!!!!!

        Intent it = new Intent(Intent.ACTION_SEND);

        //it.putExtra(Intent.EXTRA_EMAIL, "sungwg1988@naver.com");

        it.putExtra(Intent.EXTRA_TEXT, "The email body text");

        it.setType("text/plain");

        startActivity(Intent.createChooser(it, "Choose Email Client"));
        

/*
        Intent it = new Intent(Intent.ACTION_SEND);

        String[] tos = {"sungwg1988@naver.com"};

        String[] ccs = {"you@abc.com"};

        it.putExtra(Intent.EXTRA_EMAIL, tos);

        it.putExtra(Intent.EXTRA_CC, ccs);

        it.putExtra(Intent.EXTRA_TEXT, "The email body text");

        it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");

        it.setType("message/rfc822");

        startActivity(Intent.createChooser(it, "Choose Email Client"));
*/

/*
        // extra 추가하기

        Intent it = new Intent(Intent.ACTION_SEND);

        it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");

        it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");

        sendIntent.setType("audio/mp3");

        startActivity(Intent.createChooser(it, "Choose Email Client"));
*/
/*
        // 첨부파일을 추가하여 메일 보내기

        Intent it = new Intent(Intent.ACTION_SEND);

        it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");

        it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/eoe.mp3");

        sendIntent.setType("audio/mp3");

        startActivity(Intent.createChooser(it, "Choose Email Client"));
*/
        /*
        final Intent itEmail = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
        itEmail.setType("plain/text");
//수신자를 입력하세요~
        itEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"test1@a.com"});
//참조를 입력하세요~
        itEmail.putExtra(android.content.Intent.EXTRA_CC, new String[]{"test2@a.com"});
        itEmail.putExtra(Intent.EXTRA_SUBJECT, "제목을 입력하세요");
        itEmail.putExtra(Intent.EXTRA_TEXT, "내용을 입력하세요");

        ArrayList arUr = new ArrayList();
//sendList = 발송할 파일경로가 들어있는 배열
        for(String fileLoop : sendList) {
            File fileInput = new File(fileLoop)
            Uri uriFile = Uri.fromFile(fileInput);
            arUr.add(uriFile);
        }
        emailIt.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arUr);
        startActivity(Intent.createChooser(emailIt, "발송 메일 선택"));
        */
/*
        Intent msIntent = new Intent(Intent.ACTION_SEND);
        msIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        msIntent.setType("text/csv");
        msIntent.putExtra(Intent.EXTRA_EMAIL, "");
        msIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(msIntent, "메일을 발송해줘~"));
        //overridePendingTransition(R.anim.fade, R.anim.hold);
*/
    }
}
