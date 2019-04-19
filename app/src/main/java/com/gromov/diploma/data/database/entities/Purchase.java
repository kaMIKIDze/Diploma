package com.gromov.diploma.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;


@Entity
public class Purchase {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "placeAddress")
    private String retailPlaceAddress = null; // Наименование магазина
    @ColumnInfo(name = "totalSum")
    private float ecashTotalSum = 0; //общая стоимость (высчитываем из товаров или задаем сами?)
    @Ignore
    private List<Product> items; //Список покупок


    @Override
    public String toString() {
        String show = "";

        show += "Общая сумма покупки: " + (String.valueOf(this.getEcashTotalSum()) + "\n")
                + "Магазин: " + this.getRetailPlaceAddress() + "\n"
                + "Товары: " + "\n";
        return show;
    }

    public void setOwnerIdItems(long id) {
        for (int i = 0; i < this.getItems().size(); i++)
            this.getItems().get(i).setOwnerId(id);
    }


    public float getEcashTotalSum() {
        return ecashTotalSum;
    }

    public int getId() {
        return id;
    }


    public List<Product> getItems() {
        return items;
    }

    public String getRetailPlaceAddress() {
        return retailPlaceAddress;
    }


    public void setEcashTotalSum(float ecashTotalSum) {
        this.ecashTotalSum = ecashTotalSum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public void setRetailPlaceAddress(String retailPlaceAddress) {
        this.retailPlaceAddress = retailPlaceAddress;
    }
}
