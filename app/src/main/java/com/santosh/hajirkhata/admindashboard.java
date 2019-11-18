package com.santosh.hajirkhata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admindashboard extends AppCompatActivity {
    Button addf,adds,cp,assign,vf;
    //String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);
        addf=(Button)findViewById(R.id.button);
        adds =(Button)findViewById(R.id.button5);
        cp=(Button)findViewById(R.id.change_password);
        assign=(Button)findViewById(R.id.Assign_faculty);
        vf=(Button)findViewById((R.id.view_faculty));
        addf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(admindashboard.this,addfaculty.class));
            }
        });
        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(admindashboard.this,addstudent.class));
            }
        });
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(admindashboard.this,changepassword.class);
                String user= getIntent().getExtras().getString("user");
                i.putExtra("user",user);
                i.putExtra("from","admin");
                startActivity(i);
            }
        });
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(admindashboard.this,assignclass.class));
            }
        });
        vf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(admindashboard.this,viewfaculty.class));
            }
        });

    }
}
