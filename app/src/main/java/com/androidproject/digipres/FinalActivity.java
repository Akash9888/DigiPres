package com.androidproject.digipres;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class FinalActivity extends AppCompatActivity {

    TextView Date, d_Name, d_Degree, d_Field, d_Regi, d_Email, d_Mobile, p_Name, p_Mobile,
            p_Age, p_Weight, p_Height, p_Bmi, p_Bp, p_Temp, p_Pulse, p_Gender, p_Cc, p_Rr, p_Adv, p_Rx;

    Button btn_Share;
    ProgressBar progress_Bar;
    RelativeLayout Main;
    private Bitmap bitmap;


   FirebaseStorage storage;
   FirebaseDatabase database;
   FirebaseUser user;
   DatabaseReference reference;

   String number_key, date_key,  userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        Date= findViewById(R.id.date);
        show_date();


        d_Name= findViewById(R.id.doc_name);
        d_Degree= findViewById(R.id.doc_Degree);
        d_Field= findViewById(R.id.doc_field);
        d_Regi= findViewById(R.id.doc_regi);
        d_Email= findViewById(R.id.doc_email);
        d_Mobile= findViewById(R.id.doc_mobile);
        show_dr_info();

        p_Name= findViewById(R.id.p_name);
        p_Mobile= findViewById(R.id.p_mobile);
        p_Age= findViewById(R.id.p_age);
        p_Weight= findViewById(R.id.p_weight);
        p_Height= findViewById(R.id.p_height);
        p_Bmi= findViewById(R.id.p_bmi);
        p_Bp= findViewById(R.id.p_bp);
        p_Temp= findViewById(R.id.p_temp);
        p_Pulse= findViewById(R.id.p_pulse);
        p_Gender= findViewById(R.id.p_gender);
        p_Cc= findViewById(R.id.p_cc);
        p_Rr= findViewById(R.id.p_rr);
        p_Adv= findViewById(R.id.p_adv);
        p_Rx= findViewById(R.id.p_rx);
        show_patient_info();

        progress_Bar= findViewById(R.id.progress_bar);
        Main = findViewById(R.id.main);

        btn_Share = findViewById(R.id.btn_share);

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        btn_Share.setOnClickListener(v -> activity_to_photo());


    }

    private void activity_to_photo() {
        Log.d("size"," "+ Main.getWidth() +"  "+Main.getWidth());
        bitmap = loadBitmapFromView(Main, Main.getWidth(), Main.getHeight());
        createPdf();
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    private void createPdf() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;


        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.CYAN);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        @SuppressLint("SdCardPath") String targetPdf ="/sdcard/"+date_key+"_"+number_key+".pdf";

        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();


        store_pdf();
        share_pdf();

        Intent intent = new Intent (FinalActivity.this,DeciderActivity.class);
        startActivity(intent);

    }

    private void store_pdf() {

    StorageReference storageReference = storage.getReference();
     @SuppressLint("SdCardPath") File file = new File("/sdcard/"+date_key+"_"+number_key+".pdf");
     storageReference.child(number_key).child(date_key).putFile(Uri.fromFile(file));
    }

    private void share_pdf() {

        @SuppressLint("SdCardPath") File file = new File("/sdcard/"+date_key+"_"+number_key+".pdf");

        if (!file.exists()){
            Toast.makeText(this, "File doesn't exit",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("application/pdf");
        intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file));
        startActivity(Intent.createChooser(intentShare, "Share this file..."));

    }


    private void show_date() {
        java.util.Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance().format(currentTime);
        Date.setText(formattedDate);

        date_key = formattedDate.replaceAll("\\s", "");
    }

    private void show_patient_info() {
        Intent intent = getIntent();

        String Name = intent.getStringExtra("keyname");
        String Mobile = intent.getStringExtra("keyphone");
        String Age = intent.getStringExtra("keyage");
        String Weight = intent.getStringExtra("keyweight");
        String Height = intent.getStringExtra("keyheight");
        String BMI = intent.getStringExtra("keybmi");
        String BP = intent.getStringExtra("keybp");
        String Temp = intent.getStringExtra("keytemp");
        String Pulse = intent.getStringExtra("keypulse");
        String Gender = intent.getStringExtra("keygender");
        //String Nv = intent.getStringExtra("keycnt");
        String Cc = intent.getStringExtra("keycc");
        String Rr = intent.getStringExtra("keyrr");
        String Adv = intent.getStringExtra("keyadv");
        String Rx = intent.getStringExtra("keyrx");

        number_key = Mobile;



        p_Name.setText(Name);
        p_Mobile.setText(Mobile);
        //p_Nv.setText(Nv);
        p_Age.setText(Age);
        p_Weight.setText(Weight);
        p_Height.setText(Height);
        p_Bmi.setText(BMI);
        p_Bp.setText(BP);
        p_Temp.setText(Temp);
        p_Pulse.setText(Pulse);
        p_Gender.setText(Gender);
        p_Cc.setText(Cc);
        p_Rr.setText(Rr);
        p_Adv.setText(Adv);
        p_Rx.setText(Rx);


    }

    private void show_dr_info() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Doctors");
        userID = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
        Query chekUser = reference.orderByChild(userID);

        chekUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nameFromDB = snapshot.child(userID).child("doctor_name").getValue(String.class);
                String degreeFromDB = snapshot.child(userID).child("doctor_degree").getValue(String.class);
                String fieldFromDB = snapshot.child(userID).child("doctor_field").getValue(String.class);
                String regiFromDB = snapshot.child(userID).child("doctor_regi").getValue(String.class);
                String emailFromDB = snapshot.child(userID).child("doctor_email").getValue(String.class);
                String phoneFromDB = snapshot.child(userID).child("doctor_phone").getValue(String.class);

                d_Name.setText("Dr."+" "+nameFromDB);
                d_Degree.setText(degreeFromDB);
                d_Field.setText(fieldFromDB);
                d_Regi.setText("Reg:No."+" "+regiFromDB);
                d_Email.setText("Email:"+" "+emailFromDB);
                d_Mobile.setText("Mobile:"+" "+phoneFromDB);

                /*d_Name.setText(nameFromDB);
                d_Degree.setText(degreeFromDB);
                d_Field.setText(fieldFromDB);
                d_Regi.setText(regiFromDB);
                d_Email.setText(emailFromDB);
                d_Mobile.setText(phoneFromDB);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( FinalActivity.this, "Something Wrong",Toast.LENGTH_LONG).show();
            }
        });
    }

}
