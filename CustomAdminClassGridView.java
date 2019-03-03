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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CustomAdminClassGridView  extends BaseAdapter
{
    private Context mContext;
    private final String[] classname;
    private final String[] trainername;
    private final String[] classdesc;
    private final String[] classnum;



    public CustomAdminClassGridView(Context mContext, String[] classname,String [] trainername,String [] classdesc,String[]classnum) {
        this.mContext = mContext;
        this.classname = classname;
        this.trainername = trainername;
        this.classdesc =classdesc;
        this.classnum = classnum;

    }

    @Override
    public int getCount() {
        return classname.length;
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
        gridViewAndroid = inflater.inflate(R.layout.custom_adminclasses_gridview, null);

        viewholder.className= (TextView) gridViewAndroid.findViewById(R.id.adminClassNameTxt);
        viewholder.trainerName = (TextView) gridViewAndroid.findViewById(R.id.adminTrainerNameTxt);
        viewholder.classDesc = (TextView) gridViewAndroid.findViewById(R.id.adminClassDescTxt);
        viewholder.classNum = (TextView) gridViewAndroid.findViewById(R.id.adminClassNumTxt);

        viewholder.className.setText(classname[x]);
        viewholder.trainerName.setText(trainername[x]);
        viewholder.classDesc.setText(classdesc[x]);
        viewholder.classNum.setText(classnum[x]+" Students");

        viewholder.classDelete = (Button) gridViewAndroid.findViewById(R.id.AdminClassDeleteBtn);
        viewholder.classDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Class")
                        .setMessage("Are you sure you want to delete this class?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference leadersRef = FirebaseDatabase.getInstance().getReference("Classes");
                                Query query = leadersRef.orderByChild("className").equalTo(classname[x]);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot child : snapshot.getChildren())
                                        {
                                            if(child.child("className").getValue(String.class).equals(classname[x])) {
                                                child.getRef().removeValue();
                                                AdminClassesFragment.UpdateClassGridView();
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

