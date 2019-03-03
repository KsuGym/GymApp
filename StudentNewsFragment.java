package com.example.app.ksugym.Students;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.app.ksugym.Customs.CustomAdminNewsGridView;
import com.example.app.ksugym.Customs.CustomStudentsNewsGridView;
import com.example.app.ksugym.News;
import com.example.app.ksugym.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentNewsFragment extends Fragment
{
    static View view;
    static GridView newsGV;

    static CustomStudentsNewsGridView custom;
    static ArrayList<News> notiArrayList = new ArrayList<>();
    static String [] texts;
    static String [] images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_student_news, container, false);
        newsGV = view.findViewById(R.id.studentsNewsGV);

        UpdateStudentsNewsGridView();

        return view;
    }//End of OnCreate

    public static void UpdateStudentsNewsGridView()
    {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference moviesRef = rootRef.child("News");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                notiArrayList = new ArrayList<>();
                for (DataSnapshot children : dataSnapshot.getChildren())
                {
                    News n = children.getValue(News.class);
                    notiArrayList.add(n);
                }//End of for loop

                texts= new String [notiArrayList.size()];
                images = new String[notiArrayList.size()];

                for(int i=0; i < notiArrayList.size(); i++)
                {
                    News n = notiArrayList.get(i);
                    texts[i] = n.getNewsText();
                    images[i] = n.getImgUrl();
                }
                custom = new CustomStudentsNewsGridView(view.getContext(),texts,images);
                newsGV.setAdapter(custom);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        moviesRef.addListenerForSingleValueEvent(eventListener);
    }



} //End of Class
