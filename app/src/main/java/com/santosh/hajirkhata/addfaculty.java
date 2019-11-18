package com.santosh.hajirkhata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addfaculty extends AppCompatActivity {
    EditText uusername,upassword,ufname,ulname;
    String   username,password,fname,lname,cusername;
    ProgressBar loading;
    Button save;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private DocumentReference dref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfaculty);
        uusername=(EditText)findViewById(R.id.editText5);
        upassword=(EditText)findViewById(R.id.editText6);
        ufname=(EditText)findViewById(R.id.editText3);
        ulname=(EditText)findViewById(R.id.editText4);
        loading=(ProgressBar)findViewById(R.id.loading);
        save=(Button)findViewById(R.id.button2);
        loading.setVisibility(View.GONE);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getfromuser();
            }
        });

    }
    public void showall(){
        save.setVisibility(View.VISIBLE);
        uusername.setVisibility(View.VISIBLE);
        upassword.setVisibility(View.VISIBLE);
        ufname.setVisibility(View.VISIBLE);
        ulname.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }
    public void hideall(){
        save.setVisibility(View.GONE);
        uusername.setVisibility(View.GONE);
        upassword.setVisibility(View.GONE);
        ufname.setVisibility(View.GONE);
        ulname.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }
    public void getfromuser(){
        hideall();
        username=uusername.getText().toString();
        password=upassword.getText().toString();
        fname=ufname.getText().toString();
        lname=ulname.getText().toString();
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)||TextUtils.isEmpty(fname)||TextUtils.isEmpty(lname)){
            Toast.makeText(addfaculty.this,"Please Fill The Form properly",Toast.LENGTH_SHORT).show();
            showall();
            uusername.setText("");
            upassword.setText("");
        }
        else{
            checkusername();
        }
    }
    public void checkusername(){
        dref=db.collection("faculty").document(username);
        dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot dss=task.getResult();
                if (dss.exists()){
                    showalert("The username is already Taken");
                }
                else{
                    add();
                }

            }
        });
    }
    public void showalert(String message){
        showall();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog ald=alertDialogBuilder.create();
        ald.show();
    }
    public void add(){
        Map<String,String> teacher=new HashMap<>();
        teacher.put("fname",fname);
        teacher.put("lanme",lname);
        teacher.put("username",username);
        teacher.put("password",password);
        db.collection("faculty").document(username).set(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(addfaculty.this,"New faculty added successfully",Toast.LENGTH_SHORT).show();
                showall();
                uusername.setText("");
                upassword.setText("");
                ufname.setText("");
                ulname.setText("");
            }
        });
    }
}
