package com.gromov.diploma.view.products;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductInfo implements Parcelable {

    private String name;
    private int categoryId;
    private double count;
    private double cost;
    private String categoryName;

    public ProductInfo(String name, int categoryId, String categoryName, double count, double cost) {
        this.name = name;
        this.categoryId = categoryId;
        this.count = count;
        this.cost = cost;
        this.categoryName = categoryName;
    }


    public ProductInfo(Parcel in) {
       name = in.readString();
       categoryId = in.readInt();
       categoryName = in.readString();
       count = in.readDouble();
       cost = in.readDouble();

    }

    public static final Creator<ProductInfo> CREATOR = new Creator<ProductInfo>() {
        @Override
        public ProductInfo createFromParcel(Parcel in) {
            return new ProductInfo(in);
        }

        @Override
        public ProductInfo[] newArray(int size) {
            return new ProductInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeInt(categoryId);
        parcel.writeString(categoryName);
        parcel.writeDouble(count);
        parcel.writeDouble(cost);


    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public double getCount() {
        return count;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
