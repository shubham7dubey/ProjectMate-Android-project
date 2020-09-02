package com.example.project3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class Start_Page extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText ema;
    private EditText passw;
    private TextView forgotPassword;
    private Button log;
    private Button sign;
    private FirebaseAuth fAuth;
    private ProgressBar prb;
    int cnt = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__page);

        prb=(ProgressBar)findViewById(R.id.pb);
        prb.setVisibility(View.INVISIBLE);
        if (!isConnected(Start_Page.this))
        {buildDialog(Start_Page.this).show();}
        else {

            ema = (EditText) findViewById(R.id.memail);
            passw = (EditText) findViewById(R.id.mpass);
            String email, password;
            final Button log = (Button) findViewById(R.id.login);
            final Button sign = (Button) findViewById(R.id.signup);
            forgotPassword = (TextView) findViewById(R.id.reset);
            FirebaseApp.initializeApp(Start_Page.this);
            mAuth = FirebaseAuth.getInstance();


            fAuth = FirebaseAuth.getInstance();
            FirebaseUser user = fAuth.getCurrentUser();

            if (user != null && user.isEmailVerified()) {
                Intent i = new Intent(Start_Page.this, contentView.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Already SIgned In", Toast.LENGTH_SHORT).show();
                finish();
            } else {

            }


            //  Login Button Listener starts
            log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prb.setVisibility(View.VISIBLE);
                    ema.setEnabled(false);
                    passw.setEnabled(false);
                     log.setEnabled(false);
                     sign.setEnabled(false);
                     forgotPassword.setEnabled(false);
                    String email, password;
                    email  = ema.getText().toString();
                    password = passw.getText().toString();
                    if (email.isEmpty() || password.isEmpty()) {
                        prb.setVisibility(View.INVISIBLE);
                        log.setEnabled(true);
                        sign.setEnabled(true);
                        ema.setEnabled(true);
                        passw.setEnabled(true);
                        forgotPassword.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Fields Cannot remain Empty", Toast.LENGTH_LONG).show();
                    } else {
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    if (mAuth.getCurrentUser().isEmailVerified()) {
                                        Intent i = new Intent(Start_Page.this, contentView.class);

                                        startActivity(i);
                                        prb.setVisibility(View.INVISIBLE);
                                        log.setEnabled(true);
                                        sign.setEnabled(true);

                                        ema.setEnabled(true);
                                        passw.setEnabled(true);
                                        forgotPassword.setEnabled(true);
                                        finish();
                                    } else{
                                        Toast.makeText(getApplicationContext(), "Please Verify Your Email first", Toast.LENGTH_SHORT).show();
                                        prb.setVisibility(View.INVISIBLE);
                                        log.setEnabled(true);
                                        sign.setEnabled(true);

                                        ema.setEnabled(true);
                                        passw.setEnabled(true);
                                        forgotPassword.setEnabled(true);
                                    }
                                } else { prb.setVisibility(View.INVISIBLE);
                                    log.setEnabled(true);
                                    sign.setEnabled(true);
                                    forgotPassword.setEnabled(true);
                                    ema.setEnabled(true);
                                    passw.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), "UnRegistered User Please Sign UP", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });
//......Login button Listener Ends


            //......... SignUp Button Listener Starts
            sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent j = new Intent(Start_Page.this, SignUp.class);
                    startActivity(j);
                    finish();
                }
            });

            //.......SignUp Button Listener Ends

            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inr = new Intent(Start_Page.this, PasswordRese.class);
                    startActivity(inr);
                    finish();
                }
            });

            //Forgot Password listener starts


            //Forgot Password listener starts

        }
    }


    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) {
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }


    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }

}
