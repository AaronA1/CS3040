package com.adejumoa.wishify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>  {

    private final LayoutInflater mInflater;
    private List<Item> mItems;

    ItemListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ItemViewHolder holder, int position) {
        if (mItems != null) {
            Item current = mItems.get(position);
            holder.itemNameView.setText(current.getName());
            holder.itemDescriptionView.setText(current.getDescription());
            holder.itemPriceView.setText('£' + Double.toString(current.getPrice()));
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

        private final TextView itemNameView;
        private final TextView itemDescriptionView;
        private final TextView itemPriceView;

        private ItemViewHolder(View itemView) {
            super(itemView);
            itemNameView = itemView.findViewById(R.id.itemNameTV);
            itemDescriptionView = itemView.findViewById(R.id.itemDescTV);
            itemPriceView = itemView.findViewById(R.id.itemPriceTV);
        }
    }
}
