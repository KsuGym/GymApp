package com.example.app.ksugym.Students;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ksugym.Customs.CustomRateTrainerDialog;
import com.example.app.ksugym.Customs.CustomStudentsMyClassesGridView;
import com.example.app.ksugym.R;
import com.example.app.ksugym.WelcomePage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class StudentsClassDetailsFragment extends Fragment
{
    static View view;
    TextView classnametxt, trainernametxt, classdesctxt, classnumtxt;
    static String name,trainer,desc,num;

    Button rateTrainerBtn, subscribeBtn;

    public static StudentsClassDetailsFragment newInstance() {
        StudentsClassDetailsFragment fragment = new  StudentsClassDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_students_classdetails, container, false);

        classnametxt = view.findViewById(R.id.classnametxt);
        trainernametxt = view.findViewById(R.id.trainernametxt);
        classdesctxt = view.findViewById(R.id.classdesctxt);
        classnumtxt = view.findViewById(R.id.classnumtxt);

        classnametxt.setText(name);
        trainernametxt.setText(trainer);
        classdesctxt.setText(desc);
        classnumtxt.setText(num+" Students");

        rateTrainerBtn = view.findViewById(R.id.rateTrainerBtn);
        rateTrainerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomRateTrainerDialog custom = new CustomRateTrainerDialog();
                custom.showDialog(getActivity());
            }
        });


        subscribeBtn = view.findViewById(R.id.subscribeClassBtn);
        subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Subscribe")
                        .setMessage("Are you sure you want to subscribe to this class?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                addSubscribtion();//subscribe user to class

                            }

                        })//end of yes
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        return view;
    }//End of OnCreate

    public void addSubscribtion()
    {
        DatabaseReference moviesRef =  FirebaseDatabase.getInstance().getReference()
                .child("Subscribtions");
        moviesRef.child(StudentsNavigationPage.id).orderByChild("className").equalTo(name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists()) //check if user is already subscribed
                {
                    Toast.makeText(getContext(),"Student Already Subscribed to this class!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    // object to write into my database
                    final SubscribedClasses sc = new SubscribedClasses(name,trainer);

                    FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
                    final DatabaseReference ref2 = firebaseDatabase2.getReference();
                    ref2.child("Subscribtions").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ref2.child("Subscribtions").child(StudentsNavigationPage.id).push().setValue(sc);
                            Toast.makeText(getContext(), "Successfully Subscribed to class", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });




    }//end of addSubscribtion

}//end of class