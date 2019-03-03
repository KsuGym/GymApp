package com.example.app.ksugym.Admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.app.ksugym.Customs.CustomAdminNewsGridView;
import com.example.app.ksugym.News;
import com.example.app.ksugym.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminNewsFragment  extends Fragment
{
    static View view;
    Button AdminAddNewsBtn;


   static GridView newsGV;
    static CustomAdminNewsGridView custom;
  static   ArrayList<News> notiArrayList = new ArrayList<>();
   static String [] texts;
   static String [] images;


    public static AdminNewsFragment newInstance() {
        AdminNewsFragment fragment = new AdminNewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_admin_news, container, false);
        newsGV= view.findViewById(R.id.AdminNotGV);
        AdminAddNewsBtn = view.findViewById(R.id.AdminAddNotBtn);

        UpdateNewsGridView();

        AdminAddNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminAddNewsFragment fragment = AdminAddNewsFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fragment_container, fragment,"Add News")
                        .commit();
            }
        });

        return view;
    }//End of OnCreate

    public static void UpdateNewsGridView()
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
                custom = new CustomAdminNewsGridView(view.getContext(),texts,images);
                newsGV.setAdapter(custom);
                AdminNewsFragment.UpdateNewsGridView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        moviesRef.addListenerForSingleValueEvent(eventListener);
    }



} //End of Class
