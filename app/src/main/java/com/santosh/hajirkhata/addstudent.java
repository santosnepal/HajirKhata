package com.santosh.hajirkhata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addstudent extends AppCompatActivity {
    EditText fname,flname;
    Spinner facultyv;
    Button addsv;
    String fac,uname,ulname;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar loading;
    TextView tv;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);
        loading=(ProgressBar)findViewById(R.id.progressBar3);
        loading.setVisibility(View.GONE);
        addsv=(Button)findViewById(R.id.button6);
        fname=(EditText)findViewById(R.id.editText7);
        flname=(EditText)findViewById(R.id.editText8);
        facultyv=(Spinner)findViewById(R.id.spinner);
        tv=(TextView)findViewById(R.id.textView5);
        String[] arrayspinner = new String[]{
                "BIT","BCA"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,arrayspinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultyv.setAdapter(adapter);
        addsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get();
            }
        });


    }
    public void add(String name,String lname,String faculty){
        Map<String,Object> adds =new HashMap<>();
        adds.put("name",name);
        adds.put("lname",lname);
        adds.put("faculty",faculty);
        adds.put("semester","1st");
        int  Year = Calendar.getInstance().get(Calendar.YEAR);
        int id=getidn();
        String uid=String.valueOf(id)+faculty+String.valueOf(Year);
        adds.put("studentid",uid);

        Toast.makeText(addstudent.this,"Saving Data",Toast.LENGTH_SHORT).show();
        db.collection("student").add(adds).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(addstudent.this,"data Saved Successfully",Toast.LENGTH_LONG).show();
                fname.setText("");
                flname.setText("");
                show();;

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addstudent.this,"Sorry some error occoured",Toast.LENGTH_LONG).show();
            }
        });

    }
    public void get(){
        uname=fname.getText().toString();
        ulname=flname.getText().toString();
        fac=facultyv.getSelectedItem().toString();
        if (TextUtils.isEmpty(uname)||TextUtils.isEmpty(ulname)){
            Toast.makeText(addstudent.this,"Please fill form",Toast.LENGTH_SHORT).show();
        }
        else {
            hide();
            add(uname,ulname,fac);
        }


    }
    public int getidn(){
        //int count = 0;
        db.collection("student").whereEqualTo("faculty",fac).whereEqualTo("semester","1st")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult()) {
                                count++;

                            }
                        } else {
                            Toast.makeText(addstudent.this,"Sorry error occoured on genereting unique id",Toast.LENGTH_LONG).show();
                            show();
                        }
                    }
                });
        return count++;
    }
    public void show(){
        loading.setVisibility(View.GONE);
        fname.setVisibility(View.VISIBLE);
        flname.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
        facultyv.setVisibility(View.VISIBLE);
        addsv.setVisibility(View.VISIBLE);
    }
    public  void hide(){
        loading.setVisibility(View.VISIBLE);
        fname.setVisibility(View.GONE);
        flname.setVisibility(View.GONE);
        facultyv.setVisibility(View.GONE);
        addsv.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
    }
}
