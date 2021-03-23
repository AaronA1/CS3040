package com.adejumoa.wishify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Objects;

public class AddItemFragment extends Fragment {

    private TextInputLayout itemName;
    private TextInputLayout itemDescription;
    private TextInputLayout itemPrice;
    private EditText itemPlaceName;
    private Place currentPlace;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment AddItemDetails.
     */
    public static AddItemFragment newInstance() {
        return new AddItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemName = view.findViewById(R.id.item_name);
        itemDescription = view.findViewById(R.id.item_desc);
        itemPrice = view.findViewById(R.id.item_price);
        itemPlaceName = view.findViewById(R.id.item_placeName);

        Activity activity = requireActivity();
        if (activity.getIntent().hasExtra("Item")) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Item");
            Item item = (Item) activity.getIntent().getSerializableExtra("Item");
            itemName.getEditText().setText(item.getName());
            itemDescription.getEditText().setText(item.getDescription());
            itemPrice.getEditText().setText(String.valueOf(item.getPrice()));
            if (item.getPlaceName() != null)
                itemPlaceName.setText(item.getPlaceName());
        }

        setUpPlacesApi();

        view.findViewById(R.id.fab_save).setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(Objects.requireNonNull(itemName.getEditText()).getText())) {
                itemName.setError("You must enter an item name");
            } else {
                String name = itemName.getEditText().getText().toString();
                String desc = itemDescription.getEditText().getText().toString();
                double price = 0.00;
                if (!itemPrice.getEditText().getText().toString().equals("")) {
                    price = Double.parseDouble(itemPrice.getEditText().getText().toString());
                }
                Item item;
                LatLng latlng = new LatLng(0, 0);
                if (currentPlace == null) {
                    item = new Item(name, desc, price, null, 0, 0, null);
                } else {
                    latlng = currentPlace.getLatLng();
                    item = new Item(name, desc, price, currentPlace.getName(), latlng.latitude, latlng.longitude, currentPlace.getAddress());
                }


                if (activity.getIntent().hasExtra("Item")) {
                    item = (Item) activity.getIntent().getSerializableExtra("Item");
                    item.setName(name);
                    item.setDescription(desc);
                    item.setPrice(price);
                    if(currentPlace != null) {
                        item.setPlaceName(currentPlace.getName());
                        item.setPlaceLat(latlng.latitude);
                        item.setPlaceLng(latlng.longitude);
                        item.setPlaceAddress(currentPlace.getAddress());
                    }
                }
                replyIntent.putExtra("Item", item);
                activity.setResult(Activity.RESULT_OK, replyIntent);
                activity.finish();
            }
        });
    }

    public void setUpPlacesApi() {

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteFragment.setHint("Search for a place");


        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                currentPlace = place;
            }

            @Override
            public void onError(@NonNull Status status) {
                currentPlace = null;
            }
        });
    }
}