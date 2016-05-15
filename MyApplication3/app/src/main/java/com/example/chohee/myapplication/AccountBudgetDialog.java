package com.example.chohee.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by yoon on 2016-05-11.
 */
public class AccountBudgetDialog extends DialogFragment{

    private EditText budget_input;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater =LayoutInflater.from(getActivity());
        RelativeLayout root=(RelativeLayout) inflater.inflate(R.layout.dialog_dudget_input,null);
        budget_input=(EditText) root.findViewById(R.id.add_budget_input_field);

        AlertDialog.Builder b=new AlertDialog.Builder(getActivity());
        b.setView(root);
        b.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("add money",budget_input.getText().toString());
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
