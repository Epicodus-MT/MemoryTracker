package com.timberlake.memorytracker;

import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

  final static int PERMISSION_ALL;
  final static String() PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

  private GoogleMap mMap;
  MarkerOptions mo;
  Marker marker;
  LocationManager locationManager;

  // LatLng myCoordinates;
  // LocationManager locationManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_maps);
      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
              .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);
      locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

      mo = new MarkerOptions().position(new LatLng(0,0)).title("My Current Location");

      if (Build.VERSION.SDK_INT >=23 && !isPermissionGranted()) {
        requestPermissions(PERMISSIONS, PERMISSION_ALL);
      } else requestLocation();
      if(!isLocationEnabled()) {
        showAlert(1);
      }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    marker mMap.addMarker(mo);

    // LatLng sydney = new LatLng(-34 151)
    // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
  }

  @Override
  public void onLocationChanged(Location location){
    LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
    marker.setPosition(myCoordinates);
    mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));
  }

  @Override
  public void onStatusChanged(String s, int i, Bundle bundle) {

  }

  @Override
  public void onProviderEnabled(String s){

  }

  @Override
  public void onProviderDisabled(String s){

  }

  private void requestLocation() {
      Criteria criteria = new Criteria();
      criteria.setAccuracy(Criteria.ACCURACY_FINE);
      criteria.setPowerRequirement(Criteria.POWER_HIGH);
      String provider = locationManager.getBestProvider(criteria, true);
      locationManager.requestLocationUpdates(provider, 10000, 10, this);
  }
}
