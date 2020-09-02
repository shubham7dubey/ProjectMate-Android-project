package com.example.project3;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.drm.DrmManagerClient;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.*;

public class Front_Menu extends AppCompatActivity {
private FirebaseAuth mAuth;
private  ImageView disPic;
private DatabaseReference dr,drc,drcc,dRef;
private FirebaseUser mUser;
private  TextView dp,name;
private  FirebaseDatabase dbs;
    private StorageReference mStorageRef;
    private Uri filePath,downUri;
    FirebaseStorage storage;
    StorageReference storageReference,ref;


    private final int PICK_IMAGE_REQUEST = 71;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_front__menu);
         final Context context=Front_Menu.this;

         mAuth=FirebaseAuth.getInstance();
         dbs =FirebaseDatabase.getInstance();
         dRef=dbs.getReference();

         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

name= (TextView)findViewById(R.id.user);

          storage = FirebaseStorage.getInstance();
          storageReference = storage.getReference();
          mStorageRef = FirebaseStorage.getInstance().getReference();
          ref=storageReference.child(mAuth.getUid());

          dbs = FirebaseDatabase.getInstance();

         dr = FirebaseDatabase.getInstance().getReference("Users");
         drc= dr.child(mAuth.getUid());
         drc.child("Fname").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 String value = dataSnapshot.getValue(String.class);
                       if(value!=null)
                       {
                           name.setText("Welcome,  "+value.trim());

                       }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
       else
           finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.front__menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_LONG).show();
            Intent logouti=new Intent(Front_Menu.this,Start_Page.class);
            startActivity(logouti);
            finish();
            return true;
        }
        else if (id == R.id.action_profile) {

            Toast.makeText(getApplicationContext(),"profile clicked ",Toast.LENGTH_LONG).show();
           Intent logouti=new Intent(Front_Menu.this,profile.class);
            startActivity(logouti);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }








}
