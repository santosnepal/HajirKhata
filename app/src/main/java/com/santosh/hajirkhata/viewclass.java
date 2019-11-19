package com.santosh.hajirkhata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class viewclass extends AppCompatActivity {

    FirebaseFirestore db =FirebaseFirestore.getInstance();
    CollectionReference cr =db.collection("assigned");
    String cname,clname,cusername,cpassword,war;
    TextView tv,w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclass);
        war="   * Means No class For you";
        tv=(TextView)findViewById(R.id.vvvc);
        w=(TextView)findViewById(R.id.warn);
        w.setText(war);
        getfromcaller();
        show();

    }
    public void getfromcaller(){
        cname = getIntent().getExtras().getString("fname");
        clname=getIntent().getExtras().getString("lname");
        cusername=getIntent().getExtras().getString("username");
        cpassword=getIntent().getExtras().getString("password");
    }
    public void show(){
        //cr=db.collection("assigned");
        cr.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data="";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        assigned as = documentSnapshot.toObject(assigned.class);
                        String ff = as.getFaculty();
                        if(ff.equals(cusername)) {
                        String sub = as.getCode();
                        String pro = as.getProgram() + "-" + as.getSemester();
                        data += "\n      Program= " + pro + " \n      Subject= " + sub + "\n----------------------------------------------------";
                        }
                        tv.setText(data);
                    }
                }

            }
        });
    }
}
