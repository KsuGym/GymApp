package com.example.app.ksugym.Students;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.app.ksugym.Customs.CustomRateTrainerDialog;
import com.example.app.ksugym.R;

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
        subscribeBtn = view.findViewById(R.id.subscribeClassBtn);

        rateTrainerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomRateTrainerDialog custom = new CustomRateTrainerDialog();
                custom.showDialog(getActivity());
            }
        });


        return view;
    }//End of OnCreate

}//end of class