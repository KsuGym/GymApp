package com.example.app.ksugym.Customs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.app.ksugym.GMailSender;
import com.example.app.ksugym.R;
import com.example.app.ksugym.Students.StudentsNavigationPage;

public class CustomRateTrainerDialog extends Activity
{


    static Button rateBtn, rateCancelBtn;
    static EditText rateMsgTxt;
    static RatingBar ratingBar;


    static  public Activity c;
    public Dialog d;

    public  static void showDialog(final Activity activity){

        c = activity;
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_ratetrainer_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog); //To make dialog's corners rounded


        rateBtn = dialog.findViewById(R.id.sendRateBtn);
        rateCancelBtn = dialog.findViewById(R.id.rateCancelBtn);
        rateMsgTxt = dialog.findViewById(R.id.rateMsgTxt);
        ratingBar = dialog.findViewById(R.id.ratingBar);

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ratingBar.getRating() != 0) {

                    final String text = rateMsgTxt.getText().toString() + "\n Rating: " + ratingBar.getRating() + " stars" + "\n This email was sent to you from "
                            + StudentsNavigationPage.name + "\nID: " + StudentsNavigationPage.id + "\nEmail: "
                            + StudentsNavigationPage.email;
                    final ProgressDialog dialog2 = new ProgressDialog(activity);
                    dialog2.setTitle("Sending Email");
                    dialog2.setMessage("Please wait");
                    dialog2.show();
                    Thread sender = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                GMailSender sender = new GMailSender("KSUGYM2019@gmail.com", "KsuGym*123");
                                sender.sendMail("New Rate from a Student!",
                                        text,
                                        "KSUGYM2019@gmail.com",
                                        "KSUGYM2019@gmail.com");


                                dialog2.dismiss();
                            } catch (Exception e) {
                                Log.e("mylog", "Error: " + e.getMessage());
                            }
                        }
                    });
                    sender.start();
                    // Toast.makeText(activity,"Rate successfully sent",Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                }

                else {
                    Toast.makeText(activity,"Rate Must be greater than 0!",Toast.LENGTH_LONG).show();
                }

            }
        });

        rateCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show(); //Keep it at the end
    }

}
