package com.adejumoa.wishify;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;
    private LiveData<List<Item>> mAllItems;
    private final MutableLiveData<Item> selectedItem = new MutableLiveData<>();

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
    public LiveData<Item> getSelectedItem() {
        return selectedItem;
    }

    public LiveData<Item> getItem(int id) {
        return mRepository.getItem(id);
    }

    public void insert(Item item) {
        mRepository.insert(item);
    }

    public void update(Item item) {
        mRepository.update(item);
    }

    public void delete(Item item) {
        mRepository.delete(item);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

}
