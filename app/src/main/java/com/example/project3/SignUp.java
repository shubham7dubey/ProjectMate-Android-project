package com.example.project3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private EditText fname,mno,dept,roll,course,email,pass;
    private Button signup;
    private FirebaseAuth mAuth;
    private String Fname,Dept,Roll,Course;
    private String Email,password,Mno;
    private DatabaseReference dr;
    private ProgressBar prb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseApp.initializeApp(SignUp.this);
        mAuth=FirebaseAuth.getInstance();
        //dr=FirebaseDatabase.getInstance().getReference();


        fname=(EditText)findViewById(R.id.fname);
        course=(EditText)findViewById(R.id.course_Input);
        email=(EditText)findViewById(R.id.email_signup__Input);
        pass=(EditText)findViewById(R.id.password_signup__Input);
        mno=(EditText)findViewById(R.id.mob__Input);
        roll=(EditText)findViewById(R.id.roll__Input);
        dept=(EditText)findViewById(R.id.Department__Input);
        signup=(Button)findViewById(R.id.signup_btn);
        prb1=(ProgressBar)findViewById(R.id.pb1);
        prb1.setVisibility(View.INVISIBLE);
        final FirebaseUser user1 = mAuth.getCurrentUser();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prb1.setVisibility(View.VISIBLE);

                 Fname=fname.getText().toString();
                 Dept=dept.getText().toString();
                 Roll=roll.getText().toString();
                 Course=course.getText().toString();
                 Email=email.getText().toString();
                 password=pass.getText().toString();
                 Mno=mno.getText().toString();
                fname.setEnabled(false);
                course.setEnabled(false);
                email.setEnabled(false);
                pass.setEnabled(false);
                mno.setEnabled(false);
                roll.setEnabled(false);
                dept.setEnabled(false);
                signup.setEnabled(false);

                if(Fname.isEmpty() || Dept.isEmpty() || Roll.isEmpty() || Course.isEmpty() || Email.isEmpty()|| password.isEmpty() || Mno.isEmpty())
                {   show();

                    Toast.makeText(getApplicationContext(),"Feilds can't remain Empty",Toast.LENGTH_LONG).show();
                }
                else {
                    if(password.length()<6)
                    {
                         Toast.makeText(getApplicationContext(),"Password length must be atleast 6 characters",Toast.LENGTH_LONG).show();
                        show();
                    }
                    else{
                    mAuth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                addUser();

                                mAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(),"An email has been sent To you please verify your Email and then login",Toast.LENGTH_LONG).show();
                                                    Intent it=new Intent(SignUp.this,Start_Page.class);


                                                    prb1.setVisibility(View.INVISIBLE);
                                                    startActivity(it);

                                                    finish();
                                                }
                                                else{show();
                                                    Toast.makeText(SignUp.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });


                            }
                            else{
                                show();

                                Toast.makeText(getApplicationContext(),"Error: " + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

                }
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        Intent i=new Intent(SignUp.this,Start_Page.class);
        startActivity(i);
        finish();
    }
    public void addUser()
    {
        String user_id=mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_id=FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        Map newPost=new HashMap();
        newPost.put("Fname",Fname);
        newPost.put("Dept",Dept);
        newPost.put("Roll",Roll);
        newPost.put("Course",Course);
        newPost.put("Email",Email);
        newPost.put("Mno",Mno);
        current_user_id.setValue(newPost);

        DatabaseReference  current_user_id4 = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid());


        Map newPost4= new HashMap();

        newPost4.put("Counter","0");

        current_user_id4.updateChildren(newPost4);


        fname.setText("");
        mno.setText("");
        dept.setText("");
        roll.setText("");
        course.setText("");
        email.setText("");
        pass.setText("");;
    }


    public  void show()
    {
        prb1.setVisibility(View.INVISIBLE);
        fname.setEnabled(true);
        course.setEnabled(true);
        email.setEnabled(true);
        pass.setEnabled(true);
        mno.setEnabled(true);
        roll.setEnabled(true);
        dept.setEnabled(true);
        signup.setEnabled(true);
    }


}
