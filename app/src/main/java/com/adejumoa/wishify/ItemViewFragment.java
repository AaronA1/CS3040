package com.adejumoa.wishify;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ItemViewFragment extends Fragment {

    private ItemViewModel mViewModel;
    private TextView itemName;
    private TextView itemDescription;
    private TextView itemPrice;
    private TextView placeName;
    private TextView placeAddress;
    private MapView mapView;

    public static ItemViewFragment newInstance() {
        return new ItemViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_view, container, false);

        mapView = v.findViewById(R.id.mini_map);
        mapView.onCreate(savedInstanceState);

        return v;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        mViewModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
            itemName = view.findViewById(R.id.itemView_name);
            itemDescription = view.findViewById(R.id.itemView_desc);
            itemPrice = view.findViewById(R.id.itemView_price);
            placeName = view.findViewById(R.id.itemView_placeName);
            placeAddress = view.findViewById(R.id.itemView_placeAddress);
            Button expandBtn = view.findViewById(R.id.button_expand);
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemPrice.setText(String.valueOf(item.getPrice()));
            placeName.setText(item.getPlaceName());
            placeAddress.setText(item.getPlaceAddress());
            expandBtn.setText(R.string.expand_map);

            mapView.getMapAsync(googleMap -> {
                LatLng location = new LatLng(item.getPlaceLat(), item.getPlaceLng());
                float zoom = 15;
                googleMap.addMarker(new MarkerOptions().position(location).title("Store"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
            });

            expandBtn.setOnClickListener(v -> {
                getParentFragmentManager().beginTransaction()
                .replace(R.id.main_container, MapFragment.newInstance(item))
                        .addToBackStack(null)
                        .commit();
            });
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}