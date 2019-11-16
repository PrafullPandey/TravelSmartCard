package com.example.travelsmartcard.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.travelsmartcard.Activity.QRScannerActivity;
import com.example.travelsmartcard.R;

public class QR_Code_Scanner extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ImageButton qrScanner ;

    public QR_Code_Scanner() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr__code__scanner, container, false);
        qrScanner = (ImageButton) view.findViewById(R.id.qrScanner);
        qrScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , QRScannerActivity.class));
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public String toString() {
        return "QR_Code_Scanner{" +
                '}';
    }
}
