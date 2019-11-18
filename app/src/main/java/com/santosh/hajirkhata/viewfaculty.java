package com.santosh.hajirkhata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class    viewfaculty extends AppCompatActivity {

   private  FirebaseFirestore db=FirebaseFirestore.getInstance();
   private CollectionReference cref=db.collection("faculty");
   TextView tv;

    ListView lc;
    //ArrayList<DocumentSnapshot> dlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfaculty);
        tv=(TextView)findViewById(R.id.vvv);
        loadfaculty();

    }
    public void showalert(String message){
        //showall();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog ald=alertDialogBuilder.create();
        ald.show();
    }
    public void show(){
       //ArrayAdapter<String> newadp=new ArrayAdapter<String>(viewfaculty.this,android.R.layout.simple_expandable_list_item_1,dlist);
       //lc.setAdapter(newadp);
       //lc.Entr

    }
    public void loadfaculty(){
        cref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data="";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    faculty fac= documentSnapshot.toObject(faculty.class);
                    String fname=fac.getFname();
                    String lname=fac.getLanme();
                    String username=fac.getUsername();
                    String password=fac.getPassword();
                    data+="    Name: "+fname+" "+lname+"\n"+"    Username: "+username+" \n    Password: "+password+"\n----------------------------------------"+"\n\n";

                }
                tv.setText(data);

            }
        });
    }
}
