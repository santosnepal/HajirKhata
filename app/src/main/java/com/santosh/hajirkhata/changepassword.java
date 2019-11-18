package com.santosh.hajirkhata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class changepassword extends AppCompatActivity {
    EditText oldp,newp,comp;
    Button cp;
    String oldpassword,newpassword,comfirmpassword;
    String guser,who;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference drefa=db.collection("admin").document("1");
    private DocumentReference dreff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        oldp=(EditText)findViewById(R.id.editText11);
        newp=(EditText)findViewById(R.id.editText12);
        comp=(EditText)findViewById(R.id.editText13);
        cp=(Button)findViewById(R.id.button13);
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmentry();
            }
        });
    }
    public void confirmentry(){
        oldpassword=oldp.getText().toString();
        newpassword=newp.getText().toString();
        comfirmpassword=comp.getText().toString();
        if (TextUtils.isEmpty(oldpassword)||TextUtils.isEmpty(newpassword)||TextUtils.isEmpty(comfirmpassword)){
            showalert("Please Fill The form properly");
        }
        else{
            checkwho();
        }

    }
    public void checkwho(){
        who = getIntent().getExtras().getString("from");
        if(who.equals("admin")){
            checkadminold();
        }
        else{
            checkfacultyold();
        }

    }
    public void checkadminold(){
        drefa.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot daa=task.getResult();
                if(daa.exists()){
                    String old=daa.getString("password");
                    if(old.equals(oldpassword)){
                        changeadminp();
                    }
                    else{
                        showalert("old password do not match");
                    }
                }
                else{
                    showalert("Sorry Error occured");
                }
            }
        });
    }
    public void changeadminp(){
        if (newpassword.equals(comfirmpassword)){
            db.collection("admin").document("1").update("password",newpassword);
            oldp.setText("");
            showalert("Password changed successfully");
            //finish();

        }
        else{
            showalert("Password do not match");
        }
    }
    public void checkfacultyold(){
        guser=getIntent().getExtras().getString("username");
        dreff=db.collection("faculty").document(guser);
        dreff.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot dff=task.getResult();
                if (dff.exists()){
                    String oldf=dff.getString("password");
                    if (oldf.equals(oldpassword)){
                        changefp();
                    }
                    else{
                        showalert("old password donot match");
                    }
                }
                else {
                    showalert("sorry Error occoure");
                }
            }
        });
    }
    public void changefp(){
        if (newpassword.equals(comfirmpassword)){
            db.collection("faculty").document(guser).update("password",newpassword);
            showalert("Password changed successfully");
        }
        else{
            showalert("Password donot match");
        }
    }
    public void showalert(String message){
        //showall();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                oldp.setText("");
                newp.setText("");
                comp.setText("");

            }
        });
        AlertDialog ald=alertDialogBuilder.create();
        ald.show();
    }
}
