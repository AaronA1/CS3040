package com.adejumoa.wishify;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainListFragment extends Fragment {

    protected static ItemViewModel mViewModel;
    protected ItemListAdapter adapter;
    public static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_ITEM_ACTIVITY_REQUEST_CODE = 2;

    /**
     * Static constructor
     * @return A new instance of fragment MainListFragment.
     */
    public static MainListFragment newInstance() {
        return new MainListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_list);
        adapter = new ItemListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        // Update the cached copy of the items in the adapter.
        mViewModel.getAllItems().observe(getViewLifecycleOwner(), adapter::setItems);

        // Action button functionality
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            addOrEditItem(NEW_ITEM_ACTIVITY_REQUEST_CODE, null);
        });

        // Adding swipe functionality
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Item item = adapter.getItemAtPosition(position);
                        mViewModel.removeItem(position);
                        adapter.notifyDataSetChanged();
                        showUndoSnackbar(view);
                    }
                });
        helper.attachToRecyclerView(recyclerView);

        SearchView searchView = view.findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.filter("");
                return true;
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mViewModel.getAllItems().observe(getViewLifecycleOwner(), adapter::setItems);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getAllItems().observe(getViewLifecycleOwner(), adapter::setItems);
    }

    public void addOrEditItem(int reqCode, @Nullable Item extra) {
        Intent intent = new Intent(getActivity(), AddItemActivity.class);
        if (extra != null)
            intent.putExtra("Item", extra);
        startActivityForResult(intent, reqCode);
    }

    private void showUndoSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, "Undo?",
                Snackbar.LENGTH_LONG);
        snackbar.setAction("Yes", v -> {
            mViewModel.restoreRemoved();
            adapter.notifyDataSetChanged();
        });
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (event == DISMISS_EVENT_SWIPE || event == DISMISS_EVENT_TIMEOUT)
                    mViewModel.deleteRemoved();
                adapter.notifyDataSetChanged();
            }
        });
        snackbar.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == -1) {
            Item item = (Item) data.getSerializableExtra("Item");
            mViewModel.insert(item);
        } else if (requestCode == EDIT_ITEM_ACTIVITY_REQUEST_CODE && resultCode == -1) {
            Item item = (Item) data.getSerializableExtra("Item");
            mViewModel.update(item);
        } else {
            Toast.makeText(
                    getContext(),
                    "No changes made",
                    Toast.LENGTH_SHORT).show();
        }
    }
}