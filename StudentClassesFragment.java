package com.example.app.ksugym.Students;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.app.ksugym.Admin.AdminAddClassFragment;
import com.example.app.ksugym.Admin.AdminClassesFragment;
import com.example.app.ksugym.Classes;
import com.example.app.ksugym.Customs.CustomStudentsClassesGridView;
import com.example.app.ksugym.Customs.CustomStudentsNewsGridView;
import com.example.app.ksugym.News;
import com.example.app.ksugym.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentClassesFragment extends Fragment
{
    static View view;
    static  GridView classesGV;

    static CustomStudentsClassesGridView custom;
    static ArrayList<Classes> classesArrayList= new ArrayList<>();
    static String [] className,trainerName,classDesc,classNum;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_student_classes, container, false);
        classesGV = view.findViewById(R.id.StudentsClassesGV);

        UpdateStudentsClassesGV(); //Update classes gv

        classesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                StudentsClassDetailsFragment.name = className[position];
                StudentsClassDetailsFragment.trainer = trainerName[position];
                StudentsClassDetailsFragment.desc = classDesc[position];
                StudentsClassDetailsFragment.num = classNum[position];

                //go to details fragment
                StudentsClassDetailsFragment fragment = StudentsClassDetailsFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fragment_container2, fragment,"Classes")
                        .commit();

            }
        });

        return view;
    }//End of OnCreate

    public static void UpdateStudentsClassesGV()
    {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference moviesRef = rootRef.child("Classes");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                classesArrayList = new ArrayList<>();
                for (DataSnapshot children : dataSnapshot.getChildren())
                {
                    Classes c = children.getValue(Classes.class);
                    classesArrayList.add(c);
                }//End of for loop

                className =  new String [classesArrayList.size()];
                trainerName =  new String [classesArrayList.size()];
                classDesc=  new String [classesArrayList.size()];
                classNum=  new String [classesArrayList.size()];

                for(int i=0; i < classesArrayList.size(); i++)
                {
                   Classes c = classesArrayList.get(i);
                   className[i] = c.getClassName();
                   trainerName[i] = c.getTrainerName();
                   classDesc[i] = c.getClassDescription();
                   classNum[i] = c.getClassStudentsNum();
                }
                custom = new CustomStudentsClassesGridView(view.getContext(),className,trainerName);
                classesGV.setAdapter(custom);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        moviesRef.addListenerForSingleValueEvent(eventListener);
    }



} //End of Class
