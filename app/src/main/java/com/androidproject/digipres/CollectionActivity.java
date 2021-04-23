package com.androidproject.digipres;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {



    FirebaseUser user;
    DatabaseReference reference;
    String userID;


    RecyclerView recyclerView;
    MyAdapterClass myAdapterClass;
    List<Retrive_Pdf> pdf_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userID = user.getUid();




        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pdf_list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren() ){
                   Retrive_Pdf retrive_pdf = dataSnapshot1.getValue(Retrive_Pdf.class);
                    pdf_list.add(retrive_pdf);
                }
                myAdapterClass = new MyAdapterClass(CollectionActivity.this,pdf_list);
                recyclerView.setAdapter(myAdapterClass);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();

            }
        });




    }

}