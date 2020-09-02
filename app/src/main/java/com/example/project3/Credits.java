package com.example.project3;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        TextView t;

    }
    @Override
    public void onBackPressed(){
        Intent i =new Intent(Credits.this,contentView.class);
        startActivity(i);
        finish();

    }
}
