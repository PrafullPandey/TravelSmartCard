package com.example.travelsmartcard.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.travelsmartcard.Fragments.QR_Code_Scanner;
import com.example.travelsmartcard.Fragments.TripListFragment;
import com.example.travelsmartcard.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentManager fragmentManager ;
    private FragmentTransaction fragmentTransaction ;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null ;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new TripListFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment = new QR_Code_Scanner();
                    break;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //default page
        loadFragment(new QR_Code_Scanner());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
