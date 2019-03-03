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

import com.example.app.ksugym.Admin.AdminClassesFragment;
import com.example.app.ksugym.R;
import com.example.app.ksugym.Students.StudentMyClassesFragment;
import com.example.app.ksugym.Students.StudentsNavigationPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CustomStudentsMyClassesGridView extends BaseAdapter
{
    private Context mContext;
    private final String[] myclassname;
    private final String[] mytrainername;



    public CustomStudentsMyClassesGridView(Context mContext, String[] myclassname,String [] mytrainername) {
        this.mContext = mContext;
        this.myclassname = myclassname;
        this.mytrainername = mytrainername;

    }

    @Override
    public int getCount() {
        return myclassname.length;
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
        gridViewAndroid = inflater.inflate(R.layout.custom_students_myclasses, null);

        viewholder.myclassName= (TextView) gridViewAndroid.findViewById(R.id.myclassNameGV);
        viewholder.mytrainerName = (TextView) gridViewAndroid.findViewById(R.id.mytrainerNameGV);

        viewholder.myclassName.setText(myclassname[x]);
        viewholder.mytrainerName.setText(mytrainername[x]);


        viewholder.deleteMyClassBtn = (Button) gridViewAndroid.findViewById(R.id.myClassDeleteBtn);
        viewholder.deleteMyClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Unsubscribe")
                        .setMessage("Are you sure you want to unsubscribe to this class?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference leadersRef = FirebaseDatabase.getInstance().getReference()
                                        .child("Subscribtions").child(""+StudentsNavigationPage.id);
                                Query query = leadersRef.orderByChild("className").equalTo(myclassname[x]);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot child : snapshot.getChildren())
                                        {
                                            if(child.child("className").getValue(String.class).equals(myclassname[x])) {
                                                child.getRef().removeValue();
                                                StudentMyClassesFragment.updateStudentsClassesGV();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                        })//end of yes
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        return gridViewAndroid;
    }
}

