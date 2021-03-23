package com.adejumoa.wishify;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Item> mItems;
    private List<Item> mItemsCopy;
    public static final int EDIT_ITEM_ACTIVITY_REQUEST_CODE = 2;

    ItemListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ItemViewHolder holder, int position) {
        if (mItems != null) {
            Item current = mItems.get(position);
            holder.cardView.setChecked(current.isPurchased());
            holder.itemNameView.setText(current.getName());
            holder.itemDescriptionView.setText(current.getDescription());

            // Set cardview onclick listener
            holder.cardView.setOnClickListener(v -> {
                MainListFragment.mViewModel.selectItem(current);
                AppCompatActivity activity = (AppCompatActivity) holder.itemView.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, ItemViewFragment.newInstance())
                        .addToBackStack("Main")
                        .setReorderingAllowed(true)
                        .commit();
            });

            // Set onClicks for buttons
            holder.purchasedButton.setOnClickListener(v -> {
                // Toggle purchased state on press
                boolean purchased = holder.cardView.isChecked();
                current.setPurchased(!purchased);
                MainListFragment.mViewModel.update(current);
            });
            holder.editButton.setOnClickListener(v -> {
                // Start activity with edit request code
                AppCompatActivity activity = (AppCompatActivity) holder.itemView.getContext();
                MainListFragment mlf = (MainListFragment) activity.getSupportFragmentManager().getFragments().get(0);
                mlf.addOrEditItem(EDIT_ITEM_ACTIVITY_REQUEST_CODE, current);
            });
            holder.deleteButton.setOnClickListener(v -> {
                // Delete item and show toast
                MainListFragment.mViewModel.delete(current);
                Toast.makeText(v.getContext(), current.getName()+" deleted", Toast.LENGTH_SHORT).show();
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.itemNameView.setText("No Item");
        }
    }

    void setItems(List<Item> items) {
        mItems = items;
        mItemsCopy = new ArrayList<>(mItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }

    public Item getItemAtPosition(int position) {
        return mItems.get(position);
    }

    public void filter(String text) {
        if(mItems != null) {
            mItems.clear();
            if(text.isEmpty()){
                mItems.addAll(mItemsCopy);
            } else {
                text = text.toLowerCase();
                for(Item item: mItemsCopy){
                    if(item.getName().toLowerCase().contains(text)){
                        mItems.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCardView cardView;
        private final TextView itemNameView;
        private final TextView itemDescriptionView;
        private final MaterialButton purchasedButton;
        private final MaterialButton editButton;
        private final MaterialButton deleteButton;


        private ItemViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_card);
            itemNameView = itemView.findViewById(R.id.itemNameTV);
            itemDescriptionView = itemView.findViewById(R.id.itemDescTV);
            purchasedButton = itemView.findViewById(R.id.purchased_button);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
