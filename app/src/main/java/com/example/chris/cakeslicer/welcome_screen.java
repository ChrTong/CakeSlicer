package com.example.chris.cakeslicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by iryna on 2018-01-14.
 */

public class welcome_screen extends AppCompatActivity {
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        button = (Button) findViewById(R.id.next2);
        button.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), enter_num_slices.class));
            }
        });


    }}