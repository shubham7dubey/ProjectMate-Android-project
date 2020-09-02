package com.example.project3;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class showProfileImg extends AppCompatActivity {

    private CircleImageView edtimgbtn,profileimg;
    private Button save,cancel;
    private static final int imagePick=1;
    private  String downloadUrl;
    private StorageReference sr;
    private ProgressBar prp;
    private  Uri resulturi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile_img);

        setId();
        setImage();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(showProfileImg.this,profile.class);
                startActivity(i);
                finish();
            }
        });
        edtimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryPick=new Intent();
                galleryPick.setType(Intent.ACTION_GET_CONTENT);
                galleryPick.setType("image/*");
                startActivityForResult(galleryPick,imagePick);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sr= FirebaseStorage.getInstance().getReference().child("profile images").child(FirebaseAuth.getInstance().getUid());

                profileimg.setEnabled(false);
                edtimgbtn.setEnabled(false);
                cancel.setEnabled(false);
                save.setEnabled(false);
                prp.setVisibility(View.VISIBLE);

                sr.putFile(resulturi).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=uri.toString();
                                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference=firebaseDatabase.getReference();
                                    databaseReference.child("Users").child(FirebaseAuth.getInstance().getUid()).child("profile img").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                profileimg.setEnabled(true);
                                                edtimgbtn.setEnabled(true);
                                                cancel.setEnabled(true);
                                                save.setEnabled(true);
                                                prp.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getApplicationContext(),"Profile picture updated successfully.....",Toast.LENGTH_LONG).show();
                                            }
                                            else
                                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                            });
                        }

                    }
                });
            }
        });

    }
    public  void setId()
    {
        edtimgbtn=(CircleImageView)findViewById(R.id.changeProfile);
        profileimg=(CircleImageView)findViewById(R.id.bigProfileImg);
        save=(Button)findViewById(R.id.imgSave);
        cancel=(Button)findViewById(R.id.imgCancel);
        prp=(ProgressBar)findViewById(R.id.prg);
        prp.setVisibility(View.INVISIBLE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==imagePick && resultCode==RESULT_OK && data!=null)
        {
            Uri imageUri=data.getData();
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                 resulturi=result.getUri();

                Picasso.get().load(resulturi).into(profileimg);
            }
        }
    }
    private void setImage() {


        final DatabaseReference dr=FirebaseDatabase.getInstance().getReference();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image=dataSnapshot.child("Users").child(FirebaseAuth.getInstance().getUid()).child("profile img").getValue().toString();
                if(image!=null)
                    Picasso.get().load(image).into(profileimg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
