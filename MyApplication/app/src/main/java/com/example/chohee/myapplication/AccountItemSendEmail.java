package com.example.chohee.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by yoon on 2016-05-12.
 */
public class AccountItemSendEmail {

    private Context context;
    private String subject;
   static String filepath;

    public AccountItemSendEmail(Context context){
        this.context=context;
        Date today=new Date();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy년 MM월 dd일");
        subject=sf.format(today)+"여행 가계부 입니다.";
    }


    public static boolean saveExcelFile(Context context){

        Date today=new Date();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy년 MM월 dd일");
        final String fileName=String.valueOf(sf.format(today)+"여행 가계부");


       boolean success=false;

        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("myOrder");

        int cnt=0;

        // Generate column headings
        Row row = sheet1.createRow(cnt);

        c = row.createCell(0);
        c.setCellValue("날짜");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("항목");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("가격");
        c.setCellStyle(cs);

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));


         DBAdapter dbAdapter=null;
        try{
            dbAdapter=new DBAdapter(context);
            ArrayList<account_Data> itemlist  =dbAdapter.Get_Email_Data();

            for(int i=0;i<itemlist.size();i++){
                cnt++;
                row = sheet1.createRow(cnt);

                c = row.createCell(0);
                c.setCellValue(itemlist.get(i).getDate());
                c.setCellStyle(cs);

                c = row.createCell(1);
                c.setCellValue(itemlist.get(i).getItem());
                c.setCellStyle(cs);

                c = row.createCell(2);
                c.setCellValue(itemlist.get(i).getMoney());
                c.setCellStyle(cs);

                sheet1.setColumnWidth(0, (15 * 500));
                sheet1.setColumnWidth(1, (15 * 500));
                sheet1.setColumnWidth(2, (15 * 500));
            }

        }catch (Exception er){
            er.printStackTrace();
        }finally {
            dbAdapter.Close();
        }

        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
        filepath= file.getPath();
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }





    public void sendEmail(){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String email_addr = sharedPref.getString("email_address", "");

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/html");

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email_addr});

        intent.putExtra(Intent.EXTRA_SUBJECT, subject); //메일 제목

        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filepath));

        context.startActivity(intent);

    }
}
