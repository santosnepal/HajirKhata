package com.santosh.hajirkhata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class facultydashboard extends AppCompatActivity {
    Button cp,vc;
    String cname,clname,cusername,cpassword;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultydashboard);
        cp=(Button)findViewById(R.id.button11);
        vc=(Button)findViewById(R.id.button10);
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getfromcaller();
                i=new Intent(facultydashboard.this,changepassword.class);
                pass();
                startActivity(i);
            }
        });
        vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getfromcaller();
                i=new Intent(facultydashboard.this,viewclass.class);
                pass();
                startActivity(i);
            }
        });
    }
    public void getfromcaller(){
        cname = getIntent().getExtras().getString("fname");
        clname=getIntent().getExtras().getString("lname");
        cusername=getIntent().getExtras().getString("username");
        cpassword=getIntent().getExtras().getString("password");
    }
    public void pass(){
        i.putExtra("from","faculty");
        i.putExtra("fname",cname);
        i.putExtra("lname",clname);
        i.putExtra("username",cusername);
        i.putExtra("password",cpassword);

    }
}
