package com.example.project3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class StartProject extends AppCompatActivity  {
private  Button create;
private EditText titleedt,memedt,reqedt,desedt;
private  String no,name,var1;

    String var;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_project);



        DatabaseReference  current=FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("Counter");
        current.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                var1=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent intent=getIntent();
         name=intent.getStringExtra(contentView.EXTRA_TEXT);
        seddata();
        DatabaseReference dr1=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        dr1.child("Mno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                no=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        create=(Button)findViewById(R.id.createbtn);
        DatabaseReference dr;
        dr=FirebaseDatabase.getInstance().getReference().child("Counter").child("Count");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                var=dataSnapshot.getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       create.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               addProject();
           }
       });
    }
    public void  seddata()
    {
        titleedt=(EditText)findViewById(R.id.title);
        memedt=(EditText)findViewById(R.id.req);
        reqedt=(EditText)findViewById(R.id.tech);
        desedt=(EditText)findViewById(R.id.desc);

    }

    public void addProject(){
       final String s1,s2,s3,s4;

    s1=titleedt.getText().toString();
    s2=memedt.getText().toString();
    s3=reqedt.getText().toString();
    s4=desedt.getText().toString();
    if(s1.equals("Users") || s1.equals("Project") || s1.equals("Counter"))
    {
        Toast.makeText(getApplicationContext(),"Title Not Available",Toast.LENGTH_LONG).show();
    }

    else if(!s1.isEmpty()  && !s2.isEmpty() && !s3.isEmpty() && !s4.isEmpty())
    {





        DatabaseReference current_user_id = FirebaseDatabase.getInstance().getReference().child("Project");
        Map newPost = new HashMap();
        newPost.put("Project"+String.valueOf(Integer.parseInt(var)+1),s1);
        current_user_id.updateChildren(newPost);
        DatabaseReference current_user_id1 = FirebaseDatabase.getInstance().getReference().child("Counter");
        Map newPost2= new HashMap();

        newPost2.put("Count",String.valueOf(Integer.parseInt(var)+1));
        current_user_id1.setValue(newPost2);


        DatabaseReference  current_user_id2 = FirebaseDatabase.getInstance().getReference().child(s1);
        Map newPost3= new HashMap();

        newPost3.put("Member Required",s2);
        newPost3.put("Technical",s3);
        newPost3.put("Description",s4);
        newPost3.put("Admin",name+" "+no);
        newPost3.put("val",String.valueOf(Integer.parseInt(var1)));
        newPost3.put("val1",String.valueOf(Integer.parseInt(var)+1));
        current_user_id2.setValue(newPost3);



        DatabaseReference  current_user_id4 = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid());


        Map newPost4= new HashMap();

        newPost4.put("Project"+var1,s1);
        newPost4.put("Counter",String.valueOf(Integer.parseInt(var1)+1));
        current_user_id4.updateChildren(newPost4);





        Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
         Intent i=new Intent(StartProject.this,contentView.class);
         startActivity(i);
         finish();

    }
    else
    {
        Toast.makeText(getApplicationContext(),"Fields Can't remain empty",Toast.LENGTH_LONG).show();
    }
    }
    @Override
    public void onBackPressed(){
        Intent i=new Intent(StartProject.this,contentView.class);
        startActivity(i);
        finish();
    }
}
