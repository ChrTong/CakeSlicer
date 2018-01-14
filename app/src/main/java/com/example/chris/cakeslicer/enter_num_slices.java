package com.example.chris.cakeslicer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class enter_num_slices extends AppCompatActivity{

    Button button;
    EditText  num_slices;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_num_slices);

        num_slices = findViewById(R.id.NumSlices);

        button = (Button) findViewById(R.id.next);
        button.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AndroidSurfaceViewExample.class);
                intent.putExtra("NUMSLICES", num_slices.getText().toString());
                startActivity(intent);
            }
        });


    }}






