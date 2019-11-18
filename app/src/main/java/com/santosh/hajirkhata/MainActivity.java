package com.santosh.hajirkhata;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {
    Button adminlogin;
    Button facultylogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminlogin = (Button) findViewById(R.id.button3);
        facultylogin = (Button) findViewById(R.id.button4);
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,adminlogin.class));
                finish();
            }
        });
        facultylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, facultylogin.class));
                finish();
            }
        });

    }

}
