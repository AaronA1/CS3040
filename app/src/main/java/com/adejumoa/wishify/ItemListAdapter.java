package com.adejumoa.wishify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>  {

    private final LayoutInflater mInflater;
    private List<Item> mItems;

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
            holder.itemPriceView.setText("£" + current.getPrice());

            // Set short click listener
            holder.cardView.setOnClickListener(v -> {
                AppCompatActivity activity = (AppCompatActivity) holder.itemView.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, ItemViewFragment.newInstance())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack("main")
                        .commit();
            });

            // Set long click listener
            holder.cardView.setOnLongClickListener((View.OnLongClickListener) v -> {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.itemPriceView);
                popupMenu.inflate(R.menu.item_menu);
                popupMenu.setOnMenuItemClickListener(item -> {

                    if(item.getItemId() == R.id.menu_mark_purchased) {
                        Item listItem = getItemAtPosition(position);
                        listItem.setPurchased(true);
                        MainListFragment.mItemViewModel.update(listItem);
                        Toast.makeText(v.getContext(), "Mark Purchased", Toast.LENGTH_SHORT).show();
                    } else if (item.getItemId() == R.id.menu_edit_item) {
                        Toast.makeText(v.getContext(), "Edit Item", Toast.LENGTH_SHORT).show();
                    } else if (item.getItemId() == R.id.menu_delete_item) {
                        Item listItem = getItemAtPosition(position);
                        MainListFragment.mItemViewModel.delete(listItem);
                    }
                    return false;
                });
                popupMenu.show();
                return false;
            });

            // Initialise buttons
            holder.purchasedButton.setOnClickListener(v -> {
                current.setPurchased(!holder.cardView.isChecked());
                MainListFragment.mItemViewModel.update(current);
            });
            holder.editButton.setOnClickListener(v -> {
                // Implement
            });
            holder.deleteButton.setOnClickListener(v -> MainListFragment.mItemViewModel.delete(current));
        } else {
            // Covers the case of data not being ready yet.
            holder.itemNameView.setText("No Item");
        }
    }

    void setItems(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }

    public Item getItemAtPosition (int position) {
        return mItems.get(position);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCardView cardView;
        private final TextView itemNameView;
        private final TextView itemDescriptionView;
        private final TextView itemPriceView;
        private final MaterialButton purchasedButton;
        private final MaterialButton editButton;
        private final MaterialButton deleteButton;


        private ItemViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_card);
            itemNameView = itemView.findViewById(R.id.itemNameTV);
            itemDescriptionView = itemView.findViewById(R.id.itemDescTV);
            itemPriceView = itemView.findViewById(R.id.itemPriceTV);
            purchasedButton = itemView.findViewById(R.id.purchased_button);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
