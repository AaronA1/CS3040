package com.adejumoa.wishify;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Objects;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;
    private LiveData<List<Item>> mAllItems;
    private final MutableLiveData<Item> selectedItem = new MutableLiveData<>();
    private final MutableLiveData<Item> removedItem = new MutableLiveData<>();

    public ItemViewModel (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    public LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    public void selectItem(Item item) {
        selectedItem.setValue(item);
    }

    public void removeItem(int position) {
        Item item = Objects.requireNonNull(mAllItems.getValue()).remove(position);
        removedItem.setValue(item);
    }

    public void deleteRemoved() {
        delete(removedItem.getValue());
    }

    public void restoreRemoved() {
        Objects.requireNonNull(mAllItems.getValue()).add(removedItem.getValue());
    }

    public LiveData<Item> getSelectedItem() {
        return selectedItem;
    }

    public LiveData<Item> getItem(int id) {
        return mRepository.getItem(id);
    }

    public void insert(Item item) {
        item.setCreated_at(System.currentTimeMillis());
        item.setUpdated_at(System.currentTimeMillis());
        mRepository.insert(item);
    }

    public void update(Item item) {
        item.setUpdated_at(System.currentTimeMillis());
        mRepository.update(item);
    }

    public void delete(Item item) {
        mRepository.delete(item);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

}
