package com.example.app.ksugym.Students;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.app.ksugym.R;

public class StudentMyProfileFragment extends Fragment
{
    View view;

    TextView nametxt,emailtxt,idtxt,weighttxt,heighttxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        nametxt = view.findViewById(R.id.nametxt);
        emailtxt= view.findViewById(R.id.emailTxt);
        idtxt = view.findViewById(R.id.idTxt);
        weighttxt = view.findViewById(R.id.weightTxt);
        heighttxt = view.findViewById(R.id.heightTxt);

        nametxt.setText("Name: "+StudentsNavigationPage.name);
        emailtxt.setText("Email: "+StudentsNavigationPage.email);
        idtxt.setText("ID: "+StudentsNavigationPage.id);
        weighttxt.setText("Weight: "+StudentsNavigationPage.weight);
        heighttxt.setText("Height: "+StudentsNavigationPage.height);



        return view;
    }//End of OnCreate

}//end of class