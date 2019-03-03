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

import com.example.app.ksugym.Classes;
import com.example.app.ksugym.Customs.CustomAdminClassGridView;
import com.example.app.ksugym.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminClassesFragment extends Fragment
{
    static View view;
  static   GridView classesGV;
    Button addClass;


    static CustomAdminClassGridView custom;
    static  ArrayList<Classes> classArrayList = new ArrayList<>();
    static String [] classname;
    static String [] trainername;
    static String [] classdescription;
    static String [] classNum;


    public static AdminClassesFragment newInstance() {
        AdminClassesFragment fragment = new AdminClassesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_admin_classes, container, false);
        classesGV = view.findViewById(R.id.AdminClassesGV);
        addClass = view.findViewById(R.id.AdminAddClassesBtn);

        UpdateClassGridView();

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminAddClassFragment fragment = AdminAddClassFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fragment_container, fragment,"Add Class")
                        .commit();
            }
        });



        return view;
    }//End of OnCreate

    public static void UpdateClassGridView()
    {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference moviesRef = rootRef.child("Classes");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                classArrayList = new ArrayList<>();
                for (DataSnapshot children : dataSnapshot.getChildren())
                {
                   Classes c= children.getValue(Classes.class);
                    classArrayList.add(c);
                }//End of for loop

                classname =  new String [classArrayList.size()];
                trainername =  new String [classArrayList.size()];
                classdescription =  new String [classArrayList.size()];
                classNum =  new String [classArrayList.size()];

                for(int i=0; i < classArrayList.size(); i++)
                {
                    Classes c = classArrayList.get(i);
                    classname[i] = c.getClassName();
                    trainername[i] = c.getTrainerName();
                    classdescription[i] = c.getClassDescription();
                    classNum[i] = c.getClassStudentsNum();
                }
                custom = new CustomAdminClassGridView(view.getContext(),classname,trainername,classdescription,classNum);
                classesGV.setAdapter(custom);
                //AdminClassesFragment.UpdateClassGridView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        moviesRef.addListenerForSingleValueEvent(eventListener);
    }



} //End of Class
