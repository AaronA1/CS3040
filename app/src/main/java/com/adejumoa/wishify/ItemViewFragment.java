package com.adejumoa.wishify;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemViewFragment extends Fragment {

    private ItemViewModel mViewModel;

    private TextView itemName;
    private TextView itemDescription;
    private TextView itemCategory;
    private TextView itemPrice;

    public static ItemViewFragment newInstance() {
        return new ItemViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_view, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        mViewModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
            itemName = view.findViewById(R.id.itemView_name);
            itemDescription = view.findViewById(R.id.itemView_desc);
            itemCategory = view.findViewById(R.id.itemView_cat);
            itemPrice = view.findViewById(R.id.itemView_price);
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemCategory.setText(item.getCategory());
            itemPrice.setText(String.valueOf(item.getPrice()));
        });
    }

}