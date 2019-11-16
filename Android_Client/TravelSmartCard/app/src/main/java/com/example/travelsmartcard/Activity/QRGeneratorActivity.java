package com.example.travelsmartcard.Activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travelsmartcard.Model.Station;
import com.example.travelsmartcard.R;
import com.example.travelsmartcard.Util.QRCodeHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.xml.transform.Source;

public class QRGeneratorActivity extends AppCompatActivity {

    public Button generateQRButton ;
    public Bitmap bitmap ;
    public ImageView qrCodeImageView ;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("UserActive");
    private static final String TAG = "QRGeneratorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);

        generateQRButton = findViewById(R.id.generateQrCodeButton);
        qrCodeImageView = (ImageView)findViewById(R.id.qrCodeImageView);

        generateQRButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Station station = new Station(123,"IndiraNagar");

                bitmap = QRCodeHelper
                        .newInstance(QRGeneratorActivity.this)
                        .setContent(station.toString())
                        .setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
                        .setMargin(2)
                        .getQRCOde();
                qrCodeImageView.setImageBitmap(bitmap);
            }
        });
        final String UserId = "10" ;

                myRef.child(UserId).child("OpenGate").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String source = "";
                        String destination = "";
                        String opengate = "";

                        if (dataSnapshot.exists()) {
                                try {
                                    String Key = (String) dataSnapshot.getKey();
                                    String Value = (String) dataSnapshot.getValue();
                                    Log.d(TAG, "onDataChange: " + dataSnapshot);

                                    if (Key.equalsIgnoreCase("OpenGate")) {
                                        opengate = Value;
                                    } else if (Key.equalsIgnoreCase("Source")) {
                                        source = Value;
                                    } else if (Key.equalsIgnoreCase("Destination")) {
                                        destination = Value;
                                    }
                                } catch (Exception e) {
                                    Log.e(TAG, "onDataChange: " + e.getStackTrace());
                                }
                            }

                        if (opengate.equalsIgnoreCase("True" )) {
                            //SourceScanDone
                            //check minm balance logic
                            Toast.makeText(QRGeneratorActivity.this, "Source Gates Opened ", Toast.LENGTH_LONG).show();
                        } else if (opengate.equalsIgnoreCase("False")) {
                            //DestinationScan
                            //deduct balance
                            Toast.makeText(QRGeneratorActivity.this, " Exit Gates Opened", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });






    }
}
