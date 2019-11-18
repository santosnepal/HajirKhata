package com.santosh.hajirkhata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class assignclass extends AppCompatActivity {
    Spinner faculty,program,semester,subjects;
    ProgressBar load;
    Button add;
    String selfaculty,selprogram,selsemester,selsubjects;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference dref,cref;
    //DocumentReference pref,ppref;
    int iddd;
    ArrayList<String> sfaculty=new ArrayList<String>();//{"Choose Faculty"};
    ArrayList<String> fid=new ArrayList<String>();
    ArrayList<String> sprogram=new ArrayList<String>();//{"Choose Program"};
    ArrayList<String> ssemester=new ArrayList<String>();//{"Choose semester"};
    ArrayList<String> ssubjects=new ArrayList<String>();//{"Choose Subjects"};
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
        ssubjects.add("Choose Subjects");
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
        faculty.setVisibility(View.GONE);
        program.setVisibility(View.GONE);
        semester.setVisibility(View.GONE);
        subjects.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        loadfaculty();
       faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               selfaculty=faculty.getSelectedItem().toString();
               if (!selfaculty.equals("Choose Faculty")){
                   program.setVisibility(View.VISIBLE);
               }
               else {
                   program.setVisibility(View.GONE);
                   semester.setVisibility(View.GONE);
                   add.setVisibility(View.GONE);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               selprogram=program.getSelectedItem().toString();
               if(!selprogram.equals("Choose Program")){
                   semester.setVisibility(View.VISIBLE);
               }
               else {
                   semester.setVisibility(View.GONE);
                   subjects.setVisibility(View.GONE);
                   add.setVisibility(View.GONE);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               selsemester=semester.getSelectedItem().toString();
               if(!selsemester.equals("Choose Semester")){
                   subjects.setVisibility(View.VISIBLE);
                   showsub();
               }
               else {
                   subjects.setVisibility(View.GONE);
                   add.setVisibility(View.GONE);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               assign();
           }
       });

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
        //hide();
        load.setVisibility(View.VISIBLE);
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
                    fid.add(us);



                }
                faculty.setVisibility(View.VISIBLE);
            }
        });
        //show();
        load.setVisibility(View.GONE);

    }
    public void showsub(){
        String from=selprogram+"-"+selsemester;
        cref=db.collection(from);
        cref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    subjects sss=documentSnapshot.toObject(subjects.class);
                    Boolean bbb=sss.getAssigned();
                    if (bbb){}
                    else {
                        String sub1=sss.getCode();
                        ssubjects.add(sub1);
                    }
                }
                add.setVisibility(View.VISIBLE);

            }
        });

    }
    public void assign(){
        iddd=faculty.getSelectedItemPosition()-1;
        String ddd=fid.get(iddd);
        load.setVisibility(View.VISIBLE);
        faculty.setVisibility(View.GONE);
        program.setVisibility(View.GONE);
        semester.setVisibility(View.GONE);
        subjects.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        Map<String,String>  ass=new HashMap<>();
        ass.put("code",subjects.getSelectedItem().toString());
        ass.put("program",program.getSelectedItem().toString());
        ass.put("semester",semester.getSelectedItem().toString());
        ass.put("faculty",ddd);
        final Map<String,Boolean> fix=new HashMap<>();
        //fix.put("assigned",true);
        db.collection("assigned").document(program.getSelectedItem().toString()+"-"+subjects.getSelectedItem().toString()).set(ass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                db.collection(program.getSelectedItem().toString()+"-"+semester.getSelectedItem().toString()).document(subjects.getSelectedItem().toString()).update("assigned",true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showalert("class assigned successfully");
                        finish();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showalert("Sorry Error occoured during assignign class Please try again later");
                finish();
            }
        });



    }
    public void showalert(String message){
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

}
