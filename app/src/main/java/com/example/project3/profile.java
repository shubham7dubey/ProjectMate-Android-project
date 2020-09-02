package com.example.project3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {
private ImageView edit_img;
    private TextView[] tv =new TextView[6];
    private EditText[] edt =new EditText[6];
    private Button savebtn,deletebtn;
    private static int f=0;
    private static String s;
    private ProgressBar prp;
    private CircleImageView profile_image;

    private StorageReference sr;
    private DatabaseReference drc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       setId();
       setData();

        savebtn.setEnabled(false);
prp=(ProgressBar)findViewById(R.id.pbp);
prp.setVisibility(View.INVISIBLE);

for(int i=0;i<6;i++)
{
    edt[i].setVisibility(View.INVISIBLE);
}
        edit_img=(ImageView)findViewById(R.id.edtimg);
            edit_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savebtn.setEnabled(true);
                    for(int i=0;i<6;i++)
                    {
                      if(i!=1) {
                          tv[i].setVisibility(View.INVISIBLE);
                          edt[i].setVisibility(View.VISIBLE);
                      }
                      }
                    changeData();
                }
            });

savebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String[] str= new String[6];
        for(int i=0;i<6;i++)
        {
            str[i]=edt[i].getText().toString();
            if(i!=1 && str[i].isEmpty())
            {
                f=1;
                Toast.makeText(getApplicationContext(),"Field Can't Be Empty",Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(f==0)
        {
            DatabaseReference current_user_id = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());

            Map newPost = new HashMap();
            newPost.put("Course", String.valueOf(str[2]));
            newPost.put("Dept", String.valueOf(str[3]));
            newPost.put("Email",tv[1].getText().toString());
            newPost.put("Fname", String.valueOf(str[0]));
            newPost.put("Mno", String.valueOf(str[5]));
            newPost.put("Roll", String.valueOf(str[4]));


          current_user_id.setValue(newPost);

        }
Toast.makeText(getApplicationContext(),"Changes Applied Successfully",Toast.LENGTH_SHORT).show();
       setData();
        for(int i=0;i<6;i++)
        {
            if(i!=1) {
                tv[i].setVisibility(View.VISIBLE);
                edt[i].setVisibility(View.INVISIBLE);
            }
        }

    }

});


deletebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
        builder.setMessage("Do you want to delete your profile? ");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                prp.setVisibility(View.VISIBLE);
           FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).removeValue();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Account Deleted",Toast.LENGTH_LONG).show();
                                   Intent i =new Intent(profile.this,Start_Page.class);
                                   startActivity(i);
                                   finish();
                                }
                            }
                        });

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }
});


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent i=new Intent(profile.this,showProfileImg.class);
               startActivity(i);
               finish();

            }
        });
    setImage();
    }
    public void setId()
    {
        tv[0]=(TextView)findViewById(R.id.nametv);
        tv[1]=(TextView)findViewById(R.id.emailtv);
        tv[2]=(TextView)findViewById(R.id.coursetv);
        tv[3]=(TextView)findViewById(R.id.departmenttv);
        tv[4]=(TextView)findViewById(R.id.rolltv);
        tv[5]=(TextView)findViewById(R.id.contacttv);


        edt[0]=(EditText)findViewById(R.id.nameedt);
        edt[1]=(EditText)findViewById(R.id.emailedt);
        edt[2]=(EditText)findViewById(R.id.courseedt);
        edt[3]=(EditText)findViewById(R.id.departmentedt);
        edt[4]=(EditText)findViewById(R.id.rolledt);
        edt[5]=(EditText)findViewById(R.id.contactedt);
        savebtn=(Button)findViewById(R.id.save);
        deletebtn=(Button)findViewById(R.id.deleteProfile);
        profile_image=(CircleImageView)findViewById(R.id.profileImage);

    }
    public void setData()
    {
        drc= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        drc.child("Fname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueE = dataSnapshot.getValue(String.class);
                if(valueE!=null)
                {
                    tv[0].setText(valueE.trim());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        drc.child("Email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueE = dataSnapshot.getValue(String.class);
                if(valueE!=null)
                {
                    tv[1].setText(valueE.trim());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        drc.child("Course").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueE = dataSnapshot.getValue(String.class);
                if(valueE!=null)
                {
                    tv[2].setText(valueE.trim());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        drc.child("Dept").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueE = dataSnapshot.getValue(String.class);
                if(valueE!=null)
                {
                    tv[3].setText(valueE.trim());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        drc.child("Roll").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueE = dataSnapshot.getValue(String.class);
                if(valueE!=null)
                {
                    tv[4].setText(valueE.trim());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        drc.child("Mno").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueE = dataSnapshot.getValue(String.class);
                if(valueE!=null)
                {
                    tv[5].setText(valueE.trim());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void changeData()
    {
        for(int i=0;i<6;i++)
        {
            edt[i].setText(tv[i].getText().toString());
        }

    }




    private void setImage() {


        final DatabaseReference dr=FirebaseDatabase.getInstance().getReference();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try
                {String image=dataSnapshot.child("Users").child(FirebaseAuth.getInstance().getUid()).child("profile img").getValue().toString();
                if(image!=null)
                Picasso.get().load(image).into(profile_image);}
                catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent i=new Intent(profile.this,contentView.class);
        startActivity(i);
        finish();
    }
}
