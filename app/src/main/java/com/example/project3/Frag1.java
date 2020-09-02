package com.example.project3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Frag1 extends Fragment {
private static int t=3000,t1=2000;
 private ListView listView;
 private FirebaseDatabase database;
 private DatabaseReference ref;
 private ArrayList<String> str;
 private String value,value1;

public static int pos;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
 database=FirebaseDatabase.getInstance();
 ref=database.getReference().child(FirebaseAuth.getInstance().getUid());


        View v= inflater.inflate(R.layout.frag1_layout,container,false);
    final ListView listView=v.findViewById(R.id.lv);
    listView.setVisibility(View.INVISIBLE);
       final ProgressBar prb=v.findViewById(R.id.pb);

        str=new ArrayList<>();
      final  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,str);
        listView.setAdapter(adapter);
    ref.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            String value=dataSnapshot.getValue().toString();
            str.add(value);

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prb.setVisibility(View.INVISIBLE);
listView.setVisibility(View.VISIBLE);

            }
        },t);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Toast.makeText(Frag1.this.getContext(),"No. of projects",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(Frag1.this.getActivity(), showProject.class);
                    i.putExtra("ItemName", listView.getItemAtPosition(position).toString());
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar) v.findViewById(R.id.tlb);
        final ImageView imageView=(ImageView)v.findViewById(R.id.delimg);
        imageView.setVisibility(View.INVISIBLE);

listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
        imageView.setVisibility(View.VISIBLE);
        DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child(listView.getItemAtPosition(position).toString());
        dr.child("val").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            try {
             value = dataSnapshot.getValue().toString();
            }catch (Exception e){

             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference dr1=FirebaseDatabase.getInstance().getReference().child(listView.getItemAtPosition(position).toString());
        dr1.child("val1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              try{  value1=dataSnapshot.getValue().toString();}catch (Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        pos=position;
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete this project? ");
        builder.create();

prb.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
prb.setVisibility(View.INVISIBLE);
                try {


                   builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final String item = listView.getItemAtPosition(position).toString();
                            DatabaseReference dr1 = FirebaseDatabase.getInstance().getReference().child(item);
                            dr1.removeValue();

                            DatabaseReference dr2 = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("Project" + value);
                            dr2.removeValue();

                            DatabaseReference dr3 = FirebaseDatabase.getInstance().getReference().child("Project").child("Project" + String.valueOf(Integer.parseInt(value1)));
                            dr3.removeValue();
                            Toast.makeText(Frag1.this.getContext(),"Deleted... ",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(Frag1.this.getContext(),contentView.class);
                            startActivity(i);
                            Frag1.this.getActivity().finish();

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                }catch (Exception e){

                }
            }
        },t1);




        return true;
    }
});



        return v;

    }




}
