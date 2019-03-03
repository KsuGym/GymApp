package com.example.app.ksugym.Students;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ksugym.Classes;
import com.example.app.ksugym.Customs.CustomAdminClassGridView;
import com.example.app.ksugym.Customs.CustomStudentsMyClassesGridView;
import com.example.app.ksugym.R;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentMyClassesFragment extends Fragment
{
    static View view;
    static GridView myClassesGV;
    static CustomStudentsMyClassesGridView custom;

    static ArrayList<SubscribedClasses> subscribedArrayList = new ArrayList<>();
    static String [] className,trainerName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_students_myclasses, container, false);
        myClassesGV = view.findViewById(R.id.studentsMyClassesGV);

        updateStudentsClassesGV();


        return view;
    }//End of OnCreate

    public static void updateStudentsClassesGV()
    {

       DatabaseReference moviesRef =  FirebaseDatabase.getInstance().getReference()
               .child("Subscribtions").child(""+StudentsNavigationPage.id);
         ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                subscribedArrayList = new ArrayList<>();
                for (DataSnapshot children  : dataSnapshot.getChildren())
                {
                    if(children != null) {
                        String name = children.child("className").getValue().toString();
                        String trainer = children.child("trainerName").getValue().toString();
                        SubscribedClasses sc = new SubscribedClasses(name, trainer);
                        subscribedArrayList.add(sc);

                    }//end of null if

                }//End of for loop

                className =  new String [subscribedArrayList.size()];
                trainerName =  new String [subscribedArrayList.size()];

                for(int i=0; i < subscribedArrayList.size(); i++)
                {
                    SubscribedClasses c = subscribedArrayList.get(i);
                    className[i] = c.getClassName();
                    trainerName[i] = c.getTrainerName();
                }
                custom = new CustomStudentsMyClassesGridView(view.getContext(),className,trainerName);
                myClassesGV.setAdapter(custom);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        moviesRef.addListenerForSingleValueEvent(eventListener);

    }//End of update function



} //End of Class
