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
    private Item item;

    private TextView itemName;
    private TextView itemDescription;
    private TextView itemPrice;
    private TextView itemCategory;

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
            itemName.setText(item.getName());
        });
    }

}