package com.example.project3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordRese extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText Reemail;
    private Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_rese);

        ProgressBar progressBar;
        Toolbar tbl;

        mAuth=FirebaseAuth.getInstance();
        Reemail=(EditText)findViewById(R.id.Remail);
        reset=(Button)findViewById(R.id.reset_btn);
        tbl=(Toolbar)findViewById(R.id.toolbar);
        progressBar=(ProgressBar)findViewById(R.id.prog);
        progressBar.setVisibility(View.VISIBLE);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String reset_email=Reemail.getText().toString();
                if(reset_email.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Email Id",Toast.LENGTH_LONG).show();
                }
                else
                {
                    mAuth.sendPasswordResetEmail(reset_email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"Link Send",Toast.LENGTH_LONG).show();
                                        Intent inr=new Intent(PasswordRese.this,Start_Page.class);
                                        startActivity(inr);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Error: " + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });
    }
    public void onBackPressed()
    {
        Intent inr=new Intent(PasswordRese.this,Start_Page.class);
        startActivity(inr);
        finish();
    }
}
