package com.example.app.ksugym.Customs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.ksugym.Admin.AdminNewStudentFragment;
import com.example.app.ksugym.R;
import com.example.app.ksugym.Students.stuList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CustomAdminNewStudentsGV extends BaseAdapter
{
    private Context mContext;
    private final String[] name;
    private final String[] number;
    String password;


    public CustomAdminNewStudentsGV(Context mContext, String[] name, String[]number) {
        this.mContext = mContext;
        this.name = name;
        this.number = number;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int x, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder viewholder = new ViewHolder();

        gridViewAndroid = new View(mContext);
        gridViewAndroid = inflater.inflate(R.layout.custom_newstudents_gridview, null);

        viewholder.adminStudentName= (TextView) gridViewAndroid.findViewById(R.id.adminStudentName);
        viewholder.adminStudentNumber= (TextView) gridViewAndroid.findViewById(R.id.adminStudentNumber);
        viewholder.addStudent = (Button)gridViewAndroid.findViewById(R.id.adminStudentCheck);
        viewholder.deleteStudent = (Button)gridViewAndroid.findViewById(R.id.adminStudentDelete);

        viewholder.adminStudentName.setText(name[x]); //Set the names to all cells in the grid
        viewholder.adminStudentNumber.setText(number[x]);

        viewholder.addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Accept Student")
                        .setMessage("Are you sure you want to accept this new subscription?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference leadersRef = FirebaseDatabase.getInstance().getReference("Students");
                                Query query = leadersRef.orderByChild("number").equalTo(AdminNewStudentFragment.numArray[x]);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot child : snapshot.getChildren())
                                        {
                                            if(child.child("number").getValue(String.class).equals(number[x])) {

                                                for(int i = 0; i < stuList.stu.length; i++){
                                                    if(stuList.stu[i].s.number.equals(child.child("number").getValue(String.class)) ){
                                                        password =  stuList.stu[i].pass;
                                                        break;
                                                    }
                                                }
                                                //String test = child.child("subsicibed").getValue(String.class);
                                                child.getRef().child("subsicibed").setValue("yes");
                                                AdminNewStudentFragment.sendEmail(AdminNewStudentFragment.nameArray[x],
                                                        AdminNewStudentFragment.numArray[x],
                                                        password,AdminNewStudentFragment.emailArray[x]);
                                                AdminNewStudentFragment.UpdateStudents();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        //delete btn
        viewholder.deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Reject Student")
                        .setMessage("Are you sure you want to reject this new subscription?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference leadersRef = FirebaseDatabase.getInstance().getReference("Students");
                                Query query = leadersRef.orderByChild("number").equalTo(AdminNewStudentFragment.numArray[x]);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot child : snapshot.getChildren())
                                        {
                                            if(child.child("number").getValue(String.class).equals(number[x])) {
                                                for(int i = 0; i < stuList.stu.length; i++){
                                                    if(stuList.stu[i].s.number.equals(child.child("number").getValue(String.class)) ){
                                                        password =  stuList.stu[i].pass;
                                                        break;
                                                    }
                                                }
                                                child.getRef().removeValue();
                                                AdminNewStudentFragment.sendRejectEmail(AdminNewStudentFragment.nameArray[x],
                                                        AdminNewStudentFragment.numArray[x],
                                                        password,AdminNewStudentFragment.emailArray[x]);
                                                AdminNewStudentFragment.UpdateStudents();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });



        return gridViewAndroid;
    }
}

