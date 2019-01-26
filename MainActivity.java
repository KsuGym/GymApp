package com.example.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.content.Intent; //unused import statement :)!
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// import com.example.android.things;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name=(EditText)findViewById(R.id.etName);
        Password=(EditText)findViewById(R.id.etPassword);
        Login=(Button)findViewById(R.id.button2);

        Login.setOnClickListener(new View.OnClickListener() {


                                     @Override
                                     public void onClick(View v) {
validate(Name.getText().toString(),Password.getText().toString());
                                     }
                                 }

        );

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Database").child("Admin");
        reference.setValue("1234");



    }
    private void validate (String userName, String userPassowrd) //void mthod
    {
        if((userName.equals("Admin"))&&(userPassowrd.equals("1234"))) {
Intent intent =new Intent(MainActivity.this,Admin.class);
startActivity(intent);
Login.setEnabled(true);
        }
    }


}