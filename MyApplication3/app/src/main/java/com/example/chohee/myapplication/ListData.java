package com.example.chohee.myapplication;

import android.widget.TextView;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by chohee on 2016-03-17.
 */
public class ListData {
    public String  mText;
    public String mTitle;

    public static final Comparator <ListData> ALPHA_COMPARATOR=new Comparator<ListData>() {
        private final Collator sCollator=Collator.getInstance();
        @Override
        public int compare(ListData lhs, ListData rhs) {
            return sCollator.compare(lhs.mTitle,rhs.mTitle);
        }
    };
}
