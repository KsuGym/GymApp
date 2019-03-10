package com.example.app.ksugym.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app.ksugym.News;
import com.example.app.ksugym.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;

public class AdminAddNewsFragment extends Fragment {

   static View view;
    Button uploadImg, addNews;
    ImageView img;
    static EditText message;

    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    Uri filePath;
    ProgressDialog pd;
    News news;

    public static AdminAddNewsFragment newInstance() {
        AdminAddNewsFragment fragment = new AdminAddNewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_addnews, container, false);

        mStorage = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Uploading....");

        message =view.findViewById(R.id.AdminaddMsgTxt);
        img = view.findViewById(R.id.AdminAddImg);
        uploadImg =view.findViewById(R.id.AdminuploadImgBtn);
        addNews = view.findViewById(R.id.AdminAddNewsBtn);

        //upload news to firebase
        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"Please fill in news text",Toast.LENGTH_LONG).show();
                }
                else {
                    Long tsLong = System.currentTimeMillis() / 1000;
                    String ts = tsLong.toString();
                    String imgName = "image" + ts + ".jpg";
                    uploadImg(imgName);
                }
            }
        });

        //select image from gallery
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT); //set selected image to our imageview
            }
        });





        return view;
    }//End of OnCreate

    //set imgview to selected image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == getActivity().RESULT_OK  )
        {
           filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                img.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImg(String imgName) //upload image to firebase storage
    {
        //uploading the image
        if(filePath == null)
        {
            filePath = Uri.parse("android.resource://"+getContext().getPackageName()+"/drawable/admin");
        }
        pd.show();
        final StorageReference filepath = mStorage.child("Photos").child(filePath.getLastPathSegment());

        //uploading the image
        UploadTask uploadTask = filepath.putFile(filePath);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("url", "onComplete4 : " + uri.toString());
                        pd.dismiss();
                        news = new News(message.getText().toString(),uri.toString());
                        createNotification(news);//On success create noti in realtime database


                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener()//when uploading fails
        {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error in upload", Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });

        //go to gridview fragment
        AdminNewsFragment fragment = AdminNewsFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment,"News")
                .commit();

    }


   public static void createNotification (final News notification)
    {


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference ref = firebaseDatabase.getReference();

        ref.child("News").orderByChild("newsText").equalTo(message.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        if (dataSnapshot.exists()) {
                            Toast.makeText(view.getContext(), "This text already exists!", Toast.LENGTH_LONG).show();
                        } else {
                            FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
                            final DatabaseReference ref2 = firebaseDatabase2.getReference();
                            ref2.child("News").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ref2.child("News").push().setValue(notification);
                                    Toast.makeText(view.getContext(), "News Succefully Added", Toast.LENGTH_LONG).show();
                                    AdminNewsFragment.UpdateNewsGridView();
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }


}//end of class

