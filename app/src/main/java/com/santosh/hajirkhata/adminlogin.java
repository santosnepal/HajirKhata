package com.santosh.hajirkhata;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class adminlogin extends AppCompatActivity {
    Button login;
    EditText username;
    EditText password;
    TextView tv;
    ProgressBar p;
    String uname,upword,dname,dpword;
    private FirebaseFirestore fs = FirebaseFirestore.getInstance();
    private DocumentReference getref=fs.collection("admin").document("1");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        login=(Button)findViewById(R.id.log);
        username=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        tv=(TextView)findViewById(R.id.textView2);
        p=(ProgressBar)findViewById(R.id.progressBar4);
        p.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getfromuser();
            }
        });


    }
    public void getfromuser(){
        hideall();
        uname=username.getText().toString();
        upword=password.getText().toString();
        if(TextUtils.isEmpty(uname)||TextUtils.isEmpty(upword)){
            Toast.makeText(adminlogin.this,"Please fill form properly",Toast.LENGTH_SHORT).show();
            showall();

        }
        else{
           getfromcloud();
        }

    }
    public void getfromcloud(){
        hideall();
        Map<String, String> geted = new HashMap<>();
        getref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    dname=documentSnapshot.getString("username");
                    dpword=documentSnapshot.getString("password");
                    validate();
                }
                else{
                    Toast.makeText(adminlogin.this,"Sorry No Data found in Database",Toast.LENGTH_SHORT).show();
                    showall();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showall();
                Toast.makeText(adminlogin.this,"Sorry Network Issue ",Toast.LENGTH_SHORT).show();
                showall();
            }
        });

    }
    public void showall(){
        login.setVisibility(View.VISIBLE);
        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
        p.setVisibility(View.GONE);
    }
    public void hideall(){
        login.setVisibility(View.GONE);
        username.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);
    }
    public void validate(){
        if(uname.equals(dname)&upword.equals(dpword)){
            Intent i= new Intent(adminlogin.this,admindashboard.class);
            i.putExtra("user",uname);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(adminlogin.this,"Sorry Wrong credentials",Toast.LENGTH_SHORT).show();
            showall();
        }
    }



}
