package com.example.app.ksugym.Customs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.ksugym.R;


public class CustomStudentsClassesGridView extends BaseAdapter
{
    private Context mContext;
    private final String[] className;
    private final String[] trainerName;

    public CustomStudentsClassesGridView(Context mContext, String[] className,String [] trainername) {
        this.mContext = mContext;
        this.className = className;
        this.trainerName = trainername;
    }

    @Override
    public int getCount() {
        return className.length;
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
        gridViewAndroid = inflater.inflate(R.layout.custom_students_classes, null);

        viewholder.studentsClassName= (TextView) gridViewAndroid.findViewById(R.id.classNameGV);
        viewholder.studentsTrainerName = (TextView) gridViewAndroid.findViewById(R.id.trainerNameGV);

        viewholder.studentsClassName.setText(className[x]);
        viewholder.studentsTrainerName.setText(trainerName[x]);



        return gridViewAndroid;
    }
}

