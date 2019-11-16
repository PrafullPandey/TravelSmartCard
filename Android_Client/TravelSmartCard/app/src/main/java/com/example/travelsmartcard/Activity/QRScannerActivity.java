package com.example.travelsmartcard.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.travelsmartcard.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView zxingScannerView ;
    public static final int MY_CAMERA_REQUEST_CODE = 10 ;
    private static final String TAG = "QRScannerActivity";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("UserActive");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        zxingScannerView = (ZXingScannerView)findViewById(R.id.qrCodeScanner);
        setScannerProperties(zxingScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA },
                        MY_CAMERA_REQUEST_CODE);
                return ;
            }
        }
        zxingScannerView.startCamera();
        zxingScannerView.setResultHandler(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void setScannerProperties(ZXingScannerView zxingScannerView) {
        List list = new ArrayList<BarcodeFormat>();
        list.add(BarcodeFormat.QR_CODE);
        zxingScannerView.setFormats(list);
        zxingScannerView.setAutoFocus(true);
        zxingScannerView.setLaserColor(R.color.colorAccent);
        zxingScannerView.setMaskColor(R.color.colorAccent);
    }


    @Override
    public void handleResult(Result result) {
        Log.v(TAG, result.getText()); // Prints scan results
        Log.v(TAG, result.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        Toast.makeText(this,result.getText().toString(),Toast.LENGTH_LONG).show();
        zxingScannerView.stopCamera();

        sendDataToFireBase(result.getText());
        finish();
        // If you would like to resume scanning, call this method below:
//        zxingScannerView.resumeCameraPreview(this);
    }

    private void sendDataToFireBase(String text) {
        final String UserId = "10" ;
        myRef.child(UserId).child("Active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef2 = database.getReference("UserActive");



                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        try {
                            String Key = (String) messageSnapshot.getKey();
                            String Value = (String) messageSnapshot.getValue();

                            if (Key.equalsIgnoreCase("Active")) {
                                if (Value.equalsIgnoreCase("False")) {
                                    //SourceScan
                                    myRef.child(UserId).child("Source").setValue("Kaushambi");
                                    myRef.child(UserId).child("Destination").setValue("");
                                    myRef.child(UserId).child("Active").setValue("True");
                                } else if (Value.equalsIgnoreCase("True")){
                                    //DestinationScan
                                    myRef.child(UserId).child("Destination").setValue("Vaishali");
                                    myRef.child(UserId).child("Active").setValue("False");
                                    //add trip to Trips
                                    myRef.child(UserId).child("Source").setValue("");
                                    myRef.child(UserId).child("Destination").setValue("");
                                    myRef.child(UserId).child("Active").setValue("");

                                }
                            }
                        }catch (Exception e){
                            Log.e(TAG, "onDataChange: "+e.getStackTrace() );
                        }
                    }
                }else{
                    //create New One first Scan
                    myRef.child(UserId).child("Source").setValue("Kaushambi");
                    myRef.child(UserId).child("Active").setValue("True");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child(UserId).child("Source").setValue("Kaushambi");
    }
}
