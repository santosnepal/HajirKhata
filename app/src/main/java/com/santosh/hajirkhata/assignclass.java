package com.santosh.hajirkhata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class assignclass extends AppCompatActivity {
    Spinner faculty,program,semester,subjects;
    ProgressBar load;
    Button add;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference dref;
    ArrayList<String> sfaculty=new ArrayList<String>();//{"Choose Faculty"};
    ArrayList<String> sprogram=new ArrayList<String>();//{"Choose Program"};
    ArrayList<String> ssemester=new ArrayList<String>();//{"Choose semester"};
    ArrayList<String> ssubjects=new ArrayList<>();//{"Choose Subjects"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignclass);
        faculty=(Spinner)findViewById(R.id.cfs);
        program=(Spinner)findViewById(R.id.cfs2);
        semester=(Spinner)findViewById(R.id.spinner3);
        subjects=(Spinner)findViewById(R.id.spinner2);
        load=(ProgressBar)findViewById(R.id.loading);
        add=(Button)findViewById(R.id.button14);
        load.setVisibility(View.GONE);
        sfaculty.add("Choose Faculty");
        sprogram.add("Choose Program");
        ssemester.add("Choose Semester");
        sprogram.add("BCA");
        sprogram.add("BIT");
        ssemester.add("I");
        ssemester.add("II");
        ssemester.add("III");
        ssemester.add("IV");
        ssemester.add("V");
        ssemester.add("VI");
        ssemester.add("VII");
        ssemester.add("VIII");
        ArrayAdapter<String> fadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,sfaculty );
        System.out.println(sfaculty.get(0));
        fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        faculty.setAdapter(fadapter);
        ArrayAdapter<String> padapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,sprogram );
        padapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        program.setAdapter(padapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,ssemester );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(adapter);
        ArrayAdapter<String> sadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,ssubjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjects.setAdapter(sadapter);
        loadfaculty();
    }
    public void hide(){
        load.setVisibility(View.VISIBLE);
        faculty.setVisibility(View.GONE);
        program.setVisibility(View.GONE);
        semester.setVisibility(View.GONE);
        subjects.setVisibility(View.GONE);
        add.setVisibility(View.GONE);

    }
    public void show(){
        load.setVisibility(View.GONE);
        faculty.setVisibility(View.VISIBLE);
        program.setVisibility(View.VISIBLE);
        semester.setVisibility(View.VISIBLE);
        subjects.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);

    }
    public void loadfaculty(){
        hide();
        dref=db.collection("faculty");
        dref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    faculty fs=documentSnapshot.toObject(faculty.class);
                    String ns=fs.getFname();
                    String ls=fs.getLanme();
                    String us=fs.getUsername();
                    String f= ns+" "+ls+"( "+us+" )";
                    sfaculty.add(f);



                }
            }
        });
        show();

    }

}
