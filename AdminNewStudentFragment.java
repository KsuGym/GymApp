package com.example.app.ksugym.Admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.app.ksugym.Customs.CustomAdminNewStudentsGV;
import com.example.app.ksugym.EmailClasses.GMailSender;
import com.example.app.ksugym.R;
import com.example.app.ksugym.Students.Students;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminNewStudentFragment  extends Fragment {

    static View view;
    static   GridView newstudentsGV;
    static  CustomAdminNewStudentsGV customGV;

   static ArrayList <Students> studentsList = new ArrayList<>();

    public  static String [] nameArray;
    public  static String [] numArray;
    public  static String [] emailArray;
    public  static String [] weightArray;
    public  static String [] heightArray;
    public  static String [] passwordArray;
   public static String [] subscribedArray;

    static Activity c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_admin_newstudents, container, false);
        c = getActivity();
        newstudentsGV = view.findViewById(R.id.NewStudentsGridView);


        UpdateStudents();


        return view;
    }//End of OnCreate


    //rejecteEmail
    public static void sendRejectEmail(String name, String num, String password, final String email)
    {
        final String text = "Hello, "
                + name+"\nWe are sorry to inform you the admin rejected your subscription.";

        final ProgressDialog dialog2 = new ProgressDialog(c);
        dialog2.setTitle("Sending Email");
        dialog2.setMessage("Please wait");
        dialog2.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("ksugym2019@gmail.com", "KsuGym*123");
                    sender.sendMail("KSU Gym Subscription Rejection",
                            text,
                            "ksugym2019@gmail.com",
                            email); //Uploader of the book recieves the email
                    dialog2.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
        dialog2.dismiss();

        Toast.makeText(c,"Email successfully sent to student",Toast.LENGTH_LONG).show();
    }


//accepted email
    public static void sendEmail(String name, String num, String password, final String email)
    {
        final String text = "Hello, "
                + name+"\nCongrats, your KSU Gym subcription got approved! You can now Log In using your university ID."
                +"\nID: "+num+"\nPassword: "+password;


        final ProgressDialog dialog2 = new ProgressDialog(c);
        dialog2.setTitle("Sending Email");
        dialog2.setMessage("Please wait");
        dialog2.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("ksugym2019@gmail.com", "KsuGym*123");
                    sender.sendMail("KSU Gym Subscription!",
                            text,
                            "ksugym2019@gmail.com",
                            email); //Uploader of the book recieves the email
                    dialog2.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
        dialog2.dismiss();

        Toast.makeText(c,"Email successfully sent to student",Toast.LENGTH_LONG).show();
    }

    //read from firebase to fill gridview
    public  static void UpdateStudents()
    {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference moviesRef = rootRef.child("Students");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                studentsList = new ArrayList<>();
                for (DataSnapshot children : dataSnapshot.getChildren())
                {
                    if(children.child("subsicibed").getValue(String.class).equals("no")) {
                        String name = children.child("name").getValue(String.class);
                        String num = children.child("number").getValue(String.class);
                        String email = children.child("email").getValue(String.class);
                        String weight = children.child("weight").getValue(String.class);
                        String height = children.child("height").getValue(String.class);
                        String password = children.child("password").getValue(String.class);
                        String subscribed = children.child("subsicibed").getValue(String.class);
                        String notification = children.child("notification").getValue(String.class);

                        Students A = new Students(name, num,email,weight,height,password,subscribed,notification);
                        studentsList.add(A);
                    }//End of if
                }//End of for loop

                if(studentsList.size() != 0) {
                    nameArray = new String[studentsList.size()];
                    numArray =new String[studentsList.size()];
                    emailArray =  new String[studentsList.size()];
                    weightArray =  new String[studentsList.size()];
                    heightArray =  new String[studentsList.size()];
                    passwordArray= new String[studentsList.size()];
                    subscribedArray=  new String[studentsList.size()];

                    for (int i = 0; i < studentsList.size(); i++) {
                        Students x = studentsList.get(i);
                        nameArray[i] = x.getName();
                        numArray[i] = x.getNumber();
                        emailArray[i] = x.getEmail();
                        weightArray[i] = x.getWeight();
                        heightArray[i] = x.getHeight();
                        passwordArray[i] = x.getPassword();
                        subscribedArray[i] = x.getSubsicibed();
                    }

                    customGV = new CustomAdminNewStudentsGV(view.getContext(), nameArray,numArray);
                    newstudentsGV.setAdapter(customGV);
                }//End of second if
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        moviesRef.addListenerForSingleValueEvent(eventListener);

    }//end of update students


} //End of Class
