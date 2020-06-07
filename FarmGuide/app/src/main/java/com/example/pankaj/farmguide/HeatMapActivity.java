package com.example.pankaj.farmguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import java.util.ArrayList;
import java.util.List;

public class HeatMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment smf;
    GoogleMap gm;
    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);

        List<LatLng> list = null;
        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        smf.getMapAsync(this);
        list = readItems();
        mProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mOverlay = gm.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

    }
public ArrayList<LatLng> readItems()
{
    ArrayList<LatLng> list = new ArrayList<LatLng>();
    list.add(new LatLng(15.02025, 16.0222));
    list.add(new LatLng(15.025, 16.02));
    list.add(new LatLng(15.020, 16.02263));
    list.add(new LatLng(15.02015, 16.10222));
    return list;
}
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gm = googleMap;
        LatLng latLng= new LatLng(-31,111);
        gm.addMarker(new MarkerOptions().position(latLng));
        gm.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
