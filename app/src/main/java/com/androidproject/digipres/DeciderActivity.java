package com.androidproject.digipres;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeciderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView Header_Un, Header_Email;

    FirebaseUser user;
    DatabaseReference reference;
    String userID, unFromDB, emailFromDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decider);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


       show_doc_info();


        setSupportActionBar(toolbar);

        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


    }

    private void show_doc_info() {

        navigationView = findViewById(R.id.nav_view);
        View HeaderView=navigationView.getHeaderView(0);
        Header_Un = HeaderView.findViewById(R.id.header_un);
        Header_Email = HeaderView.findViewById(R.id.header_email);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Doctors");
        userID = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
        Query chekUser = reference.orderByChild(userID);

        chekUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                unFromDB = snapshot.child(userID).child("doctor_un").getValue(String.class);
                emailFromDB = snapshot.child(userID).child("doctor_email").getValue(String.class);

                Header_Un.setText(unFromDB);
                Header_Email.setText(emailFromDB);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( DeciderActivity.this, "Something Wrong",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case  R.id.nav_home:
                break;

            case R.id.nav_profile:
                Intent intent = new Intent(DeciderActivity.this,ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_logout:

                SessionManagement sessionManagement = new SessionManagement(DeciderActivity.this);
                sessionManagement.removeSession();
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(DeciderActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_rate:
                Toast.makeText(this,"Rate", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_share:
                Toast.makeText(this,"Share", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }

    public void new_patient(View view) {
        Intent intent = new Intent(DeciderActivity.this,PatientActivity.class);
        startActivity(intent);
    }

    public void exsisting_patient(View view) {
        Intent intent = new Intent(DeciderActivity.this,ExsistingPatient_Activity.class);
        startActivity(intent);
    }
}