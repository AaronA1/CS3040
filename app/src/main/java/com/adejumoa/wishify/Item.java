package com.adejumoa.wishify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "item_table")
public class Item implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private double price;

    @Nullable
    @ColumnInfo(name = "placeName")
    private String placeName;

    @Nullable
    @ColumnInfo(name = "placeLat")
    private double placeLat;

    @Nullable
    @ColumnInfo(name = "placeLng")
    private double placeLng;

    @Nullable
    @ColumnInfo(name = "placeAddress")
    private String placeAddress;

    @ColumnInfo(name = "purchased")
    private boolean purchased = false;

    @ColumnInfo(name = "created_at")
    private long created_at;

    @ColumnInfo(name = "updated_at")
    private long updated_at;

    public Item(@NonNull String name, @Nullable String description, double price,
                @Nullable String placeName, double placeLat, double placeLng, @Nullable String placeAddress) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.placeName = placeName;
        this.placeLat = placeLat;
        this.placeLng = placeLng;
        this.placeAddress = placeAddress;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Nullable
    public String getPlaceName() {
        return this.placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Nullable
    public double getPlaceLat() {
        return this.placeLat;
    }

    public void setPlaceLat(double placeLat) {
        this.placeLat = placeLat;
    }

    @Nullable
    public double getPlaceLng() {
        return this.placeLng;
    }

    public void setPlaceLng(double placeLng) {
        this.placeLng = placeLng;
    }

    @Nullable
    public String getPlaceAddress() {
        return this.placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public boolean isPurchased() {
        return this.purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public long getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

}
