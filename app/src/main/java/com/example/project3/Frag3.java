package com.example.project3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Frag3 extends Fragment {
    private EditText editText;
    private Button btn;
    private ListView listView;
    private String name;
    private FirebaseAuth mAuth;
    private String str;
    private ArrayList<String> str1;
    private DatabaseReference ref;
    private DatabaseReference myref;
    private FirebaseDatabase database;
    private ScrollView scrollView;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.frag3_layout,container,false);

        listView=(ListView)v.findViewById(R.id.lv) ;
        btn=(Button)v.findViewById(R.id.disbtn);
        mAuth=FirebaseAuth.getInstance();
        editText=(EditText) v.findViewById(R.id.disedt);
        scrollView=(ScrollView)v.findViewById(R.id.chat_scroll_view);

        database= FirebaseDatabase.getInstance();
        ref=database.getReference().child("Discussion");

        str1=new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,str1);
        listView.setAdapter(adapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value=dataSnapshot.getValue().toString();
                str1.add(value);

                adapter.notifyDataSetChanged();
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String value=dataSnapshot.getValue().toString();
                str1.add(value);

                adapter.notifyDataSetChanged();
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
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

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Fname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Dc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                str=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id=mAuth.getUid();
                DatabaseReference current_user_id=FirebaseDatabase.getInstance().getReference().child("Discussion");
                Map hashmap = new HashMap();

                 String message=editText.getText().toString();
                hashmap.put("Text"+str,name+"\n\n\t" +
                        ""+message+"\n");

                FirebaseDatabase.getInstance().getReference().child("Dc").setValue(String.valueOf(Integer.parseInt(str)+1));
                current_user_id.updateChildren(hashmap);
               editText.setText(" ");

            }

        });





        scrollView.fullScroll(ScrollView.FOCUS_DOWN);


        return v;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        /* remainder is unchanged */

        convertView.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.GRAY);
        return convertView;
    }
}
