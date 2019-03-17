package com.example.app.ksugym.Admin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.ksugym.Classes;
import com.example.app.ksugym.Customs.CustomAdminClassGridView;
import com.example.app.ksugym.NotificationGenerator;
import com.example.app.ksugym.R;
import com.example.app.ksugym.Students.StudentsClassDetailsFragment;
import com.example.app.ksugym.WelcomePage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminSendNotificationsFragment  extends Fragment
{
    static View view;
   public  static EditText message;
    Button sendBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_admin_notifications, container, false);

        message = view.findViewById(R.id.notiMsgTxt);
        sendBtn = view.findViewById(R.id.sendNotiBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !message.getText().toString().equals("")) {

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    final DatabaseReference ref = firebaseDatabase.getReference();
                    ref.child("Students")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    for (DataSnapshot child : dataSnapshot.getChildren())
                                    {
                                    child.getRef().child("notification").setValue(message.getText().toString());
                                    Toast.makeText(getContext(), "Notification Sent Successfully!", Toast.LENGTH_SHORT).show();

                                        //go to details fragment
                                        AdminNewsFragment fragment = AdminNewsFragment.newInstance();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction ft = fragmentManager.beginTransaction();
                                        ft.replace(R.id.fragment_container, fragment,"Classes")
                                                .commit();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(getContext(), "Please enter notification message", Toast.LENGTH_SHORT).show();
                }

            }//end of onclick button
        });//end of button


        return view;
    }//End of OnCreate





} //End of Class
