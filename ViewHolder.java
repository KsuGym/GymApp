package com.example.app.ksugym.Customs;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//Use for custom Gridview, to solve duplicate problem
public class ViewHolder
{
    //Admin New Students GridView
    public TextView adminStudentName;
    public TextView adminStudentNumber;
    public Button addStudent;
    public Button deleteStudent;

    //Admin Classes GridView
    public TextView className;
    public TextView trainerName;
    public TextView classDesc;
    public TextView classNum;
    public Button classDelete;

    //Admin News GridView
    public TextView NotiTxt;
    public ImageView NotiImage;
    public Button NotiDelete;


    //Students News GridView
    public TextView studentsNotiTxt;
    public ImageView studentsNotiImage;


    //Students News GridView
    public TextView studentsClassName;
    public TextView studentsTrainerName;


    //Students MyClasses GridView
    public TextView myclassName;
    public TextView mytrainerName;
    public Button deleteMyClassBtn;
    public Button rateTrainerBtn;
}
