package com.example.app.ksugym.Customs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.ksugym.R;
import com.squareup.picasso.Picasso;

public class CustomStudentsNewsGridView extends BaseAdapter
{
    private Context mContext;
    private final String[] notiTxt;
    private final String[] notiImgUrl;

    public CustomStudentsNewsGridView(Context mContext, String[] notiTxt, String[]notiImgUrl) {
            this.mContext = mContext;
            this.notiTxt = notiTxt;
            this.notiImgUrl = notiImgUrl;
        }

        @Override
        public int getCount() {
            return notiTxt.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int x, View convertView, ViewGroup parent) {
            View gridViewAndroid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ViewHolder viewholder = new ViewHolder();

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.custom_students_news, null);

            viewholder.studentsNotiTxt= (TextView) gridViewAndroid.findViewById(R.id.StudentsNewsTxt);
            viewholder.studentsNotiImage = (ImageView) gridViewAndroid.findViewById(R.id.StudentsNewsImage);

            viewholder.studentsNotiTxt.setText(notiTxt[x]);
            Picasso.get().load(notiImgUrl[x]).into(viewholder.studentsNotiImage);


            return gridViewAndroid;
        }
}

