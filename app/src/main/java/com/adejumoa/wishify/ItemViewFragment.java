package com.adejumoa.wishify;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.card.MaterialCardView;

public class ItemViewFragment extends Fragment {

    private ItemViewModel mViewModel;
    private MapView mapView;

    public static final int EDIT_ITEM_ACTIVITY_REQUEST_CODE = 2;


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
            TextView itemName = view.findViewById(R.id.itemView_name);
            TextView itemDescription = view.findViewById(R.id.itemView_desc);
            TextView itemPrice = view.findViewById(R.id.itemView_price);
            CheckBox itemPurchased = view.findViewById(R.id.itemView_purchased);
            TextView placeName = view.findViewById(R.id.itemView_placeName);
            TextView placeAddress = view.findViewById(R.id.itemView_placeAddress);
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemPrice.setText(getString(R.string.price_format, item.getPrice()));
            itemPurchased.setChecked(item.isPurchased());
            placeName.setText(item.getPlaceName());
            placeAddress.setText(item.getPlaceAddress());
            Button expandBtn = view.findViewById(R.id.button_expand);
            Button editBtn = view.findViewById(R.id.button_edit);
            Button deleteBtn = view.findViewById(R.id.button_delete);

            itemPurchased.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setPurchased(isChecked);
                mViewModel.update(item);
            });

            drawMap(item.getPlaceLat(), item.getPlaceLng());

            expandBtn.setOnClickListener(v -> {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_container, MapFragment.newInstance(item))
                        .addToBackStack(null)
                        .commit();
            });
            editBtn.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                intent.putExtra("Item", item);
                startActivityForResult(intent, EDIT_ITEM_ACTIVITY_REQUEST_CODE);
            });
            deleteBtn.setOnClickListener(v -> {
                mViewModel.delete(item);
                getParentFragmentManager().popBackStack();
            });
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_ITEM_ACTIVITY_REQUEST_CODE && resultCode == -1) {
            Item item = (Item) data.getSerializableExtra("Item");
            mViewModel.update(item);
            drawMap(item.getPlaceLat(), item.getPlaceLng());
        } else {
            Toast.makeText(
                    getContext(),
                    "No changes made",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Redraw map with Marker at given LatLng
     * @param lat Latitude Point
     * @param lng Longitude Point
     */
    private void drawMap(double lat, double lng) {
        mapView.getMapAsync(googleMap -> {
            LatLng location = new LatLng(lat, lng);
            float zoom = 15;
            googleMap.addMarker(new MarkerOptions().position(location).title("Store"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
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