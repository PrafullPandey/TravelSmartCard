package com.example.travelsmartcard.Activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.travelsmartcard.Model.Station;
import com.example.travelsmartcard.R;
import com.example.travelsmartcard.Util.QRCodeHelper;
import com.google.gson.Gson;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRGeneratorActivity extends AppCompatActivity {

    public Button generateQRButton ;
    public Bitmap bitmap ;
    public ImageView qrCodeImageView ;

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

    }
}
