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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class facultylogin extends AppCompatActivity {
    Button login;
    EditText usernamev;
    EditText passwordv;
    ProgressBar loading;
    String username,password,cusername,cpassword,cfname,clname;
    TextView tv;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private DocumentReference dref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultylogin);
        usernamev=(EditText)findViewById(R.id.editText9);
        passwordv=(EditText)findViewById(R.id.editText10);
        login=(Button) findViewById(R.id.button7);
        loading=(ProgressBar)findViewById(R.id.progressBar);
        tv=(TextView)findViewById(R.id.textView4);
        loading.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideall();
                getfromuser();
            }
        });
    }
    public void getfromcloud(){
       dref= db.collection("faculty").document(username);
       dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){
                   DocumentSnapshot dss=task.getResult();
                   if(dss.exists()){
                       cusername=dss.getString("username");
                       cpassword=dss.getString("password");
                       cfname=dss.getString("fname");
                       clname=dss.getString("lanme");
                       System.out.println(cusername+" "+cpassword+" "+cfname+" "+clname);
                       validate();
                       //Toast.makeText(facultylogin.this,"Found",Toast.LENGTH_SHORT).show();


                   }
                   else{
                       Toast.makeText(facultylogin.this,"Sorry you were not found",Toast.LENGTH_SHORT).show();
                       showall();
                   }
               }
               else{
                   Toast.makeText(facultylogin.this,"Sorry error occured"+task.getException(),Toast.LENGTH_SHORT).show();
                   showall();
               }
           }
       });





    }
    public void showall(){
        login.setVisibility(View.VISIBLE);
        usernamev.setVisibility(View.VISIBLE);
        passwordv.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }
    public void hideall(){
        login.setVisibility(View.GONE);
        usernamev.setVisibility(View.GONE);
        passwordv.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }
    public void getfromuser(){
        username=usernamev.getText().toString();
        password=passwordv.getText().toString();
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            Toast.makeText(facultylogin.this,"Please Provide all data",Toast.LENGTH_SHORT).show();
        }
        else{
            getfromcloud();
        }
    }
    public void validate(){
        if(username.equals(cusername)&&password.equals(cpassword)){
            Toast.makeText(facultylogin.this,"Welcome user",Toast.LENGTH_LONG).show();
            Intent i= new Intent(facultylogin.this,facultydashboard.class);
            i.putExtra("fname",cfname);
            i.putExtra("lname",clname);
            i.putExtra("username",cusername);
            i.putExtra("password",password);
            startActivity(i);
            finish();

        }
        else{
            Toast.makeText(facultylogin.this,"Sorry Wrong password",Toast.LENGTH_LONG).show();
            showall();
        }

    }
}
