package com.example.project3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showProject extends AppCompatActivity {
private TextView title,admin,req,tech,desc;
private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project);
        title=(TextView)findViewById(R.id.showTitle);
        admin=(TextView)findViewById(R.id.showAdmin);
        req=(TextView)findViewById(R.id.showMem);
        tech=(TextView)findViewById(R.id.showTech);
        desc=(TextView)findViewById(R.id.showDesc);

        img=(ImageView)findViewById(R.id.back);
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.tl);

        Bundle bundle=getIntent().getExtras();
        toolbar.setTitle("Projects");
        if(bundle!=null)
        {
            title.setText(bundle.getString("ItemName"));
            DatabaseReference dr= FirebaseDatabase.getInstance().getReference().child(bundle.getString("ItemName"));
            dr.child("Admin").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    admin.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            dr.child("Member Required").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    req.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            dr.child("Technical").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tech.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            dr.child("Description").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    desc.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(showProject.this,contentView.class);
                    startActivity(i);
                    finish();
                }
            });

        }
    }

@Override
    public void onBackPressed()
{
    Intent i=new Intent(showProject.this,contentView.class);
    startActivity(i);
    finish();
}
}
