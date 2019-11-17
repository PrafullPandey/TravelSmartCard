package com.example.travelsmartcard.Activity;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.example.travelsmartcard.Model.Modal_LatLng;
import com.example.travelsmartcard.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SearchView searchView;
    private static final String TAG = "MapsActivity";
    String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=Metro and Bus Stop&key=%s";
    public ArrayList<Modal_LatLng> location = new ArrayList<>();
    public Modal_LatLng modal_latLng ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        String formated_url = String.format(url,getResources().getString(R.string.google_key));
        formated_url = formated_url.replaceAll(" ","+");

        GetData getData = new GetData();
        getData.execute(formated_url);
    }

    private void addMarkerToMap(Modal_LatLng modal_latLng) {
        Double lat = modal_latLng.getLatitude();
        Double lng = modal_latLng.getLongitude();
        String name = modal_latLng.getName();

        //add marker
        LatLng result = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(result).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(result));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10.0f));



    }


    public class GetData extends AsyncTask<String , Void , Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: "+location.toString());
            // add marker with label on to map
            for(int i = 0 ; i<(location.size()>25?25:location.size());i++) {
                addMarkerToMap(location.get(i));
            }


        }

        private void addMarkerToMap(Modal_LatLng modal_latLng) {
            Double lat = modal_latLng.getLatitude();
            Double lng = modal_latLng.getLongitude();
            String name = modal_latLng.getName();

            //add marker
            LatLng result = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(result).title(name));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(result));
            mMap.animateCamera(CameraUpdateFactory.zoomBy(10.0f));



        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: ");
            URL url ;
            HttpURLConnection httpURLConnection =null;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    total.append(line);
                }
                Log.d(TAG, "doInBackground: "+total);
                parseJSONForLocation(total.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        private void parseJSONForLocation(String s) throws JSONException {

            JSONObject data = new JSONObject(s);
            JSONArray results = data.getJSONArray("results");
            for(int i=0 ; i<results.length();i++){
                try{
                    JSONObject index = results.getJSONObject(i);

                    Double lat = index.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    Double lng = index.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                    String name = index.getString("name");
                    modal_latLng = new Modal_LatLng();
                    modal_latLng.setLatitude(lat);
                    modal_latLng.setLongitude(lng);
                    modal_latLng.setName(name);
                    location.add(modal_latLng);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
