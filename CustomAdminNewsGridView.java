package com.example.app.ksugym.Customs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.ksugym.Admin.AdminNewStudentFragment;
import com.example.app.ksugym.Admin.AdminNewsFragment;
import com.example.app.ksugym.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CustomAdminNewsGridView extends BaseAdapter
{
    private Context mContext;
    private final String[] notiTxt;
    private final String[] notiImgUrl;



    public CustomAdminNewsGridView(Context mContext, String[] notiTxt,String[]notiImgUrl) {
        this.mContext = mContext;
        this.notiTxt = notiTxt;
        this.notiImgUrl = notiImgUrl;
    }

    @Override
    public int getCount() {
        return notiTxt.length;
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
        gridViewAndroid = inflater.inflate(R.layout.custom_admin_news, null);

        viewholder.NotiTxt= (TextView) gridViewAndroid.findViewById(R.id.SchoolNewsTxt);
        viewholder.NotiImage = (ImageView) gridViewAndroid.findViewById(R.id.SchoolNewsImage);

        viewholder.NotiTxt.setText(notiTxt[x]);
        Picasso.get().load(notiImgUrl[x]).into(viewholder.NotiImage);

        viewholder.NotiDelete = (Button) gridViewAndroid.findViewById(R.id.AdminNotiDeleteBtn);
        viewholder.NotiDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete News")
                        .setMessage("Are you sure you want to delete this news?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference leadersRef = FirebaseDatabase.getInstance().getReference("News");
                                Query query = leadersRef.orderByChild("newsText").equalTo(AdminNewStudentFragment.numArray[x]);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot child : snapshot.getChildren())
                                        {
                                            if(child.child("newsText").getValue(String.class).equals(notiTxt[x])) {

                                                child.getRef().removeValue();
                                                AdminNewsFragment.UpdateNewsGridView();
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

