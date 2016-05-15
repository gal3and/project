package com.example.chohee.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class TipActivity extends Fragment {

    List<Integer> gallery = new ArrayList<Integer>();

    private ImageView Tipimage;
    private Gallery Tipgallery;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tiplayout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        for (int i = 1; i < 3; i++) {

            gallery.add(getResources().getIdentifier("t"+i, "drawable", getActivity().getPackageName()));

        }
        Tipimage = (ImageView) view.findViewById(R.id.tipimage);
        Tipgallery = (Gallery) view.findViewById(R.id.tipgallery);

        Tipgallery.setAdapter(new galleryAdapter(getActivity()));
        Tipgallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tipimage.setImageResource(gallery.get(position));
            }
        });

    }





    class galleryAdapter extends BaseAdapter {
        private Context mContext = null;
        LayoutInflater inflater;

        public galleryAdapter(Context c) {
            mContext = c;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {


            return gallery.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=inflater.inflate(R.layout.gallery_item,parent,false);

            }
            ImageView i =(ImageView) convertView.findViewById(R.id.tipgalleryitem);
            i.setImageResource(gallery.get(position));


            return convertView;
        }
    }
}