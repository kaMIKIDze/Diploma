package com.gromov.diploma;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;


@Entity
public class Purchase {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private int category = 0;  // Выбор категории товаров в покупке (продукты, спорт и т.д.)
    @ColumnInfo(name = "placeAddress")
    private String retailPlaceAddress = null; // Наименование магазина
    @ColumnInfo(name = "totalSum")
    private float ecashTotalSum = 0; //общая стоимость (высчитываем из товаров или задаем сами?)
    @Ignore
    private ArrayList<Product> items; //Список покупок


    public String showPurchase() {
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

    public int getCategory() {
        return category;
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public String getRetailPlaceAddress() {
        return retailPlaceAddress;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setEcashTotalSum(float ecashTotalSum) {
        this.ecashTotalSum = ecashTotalSum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }

    public void setRetailPlaceAddress(String retailPlaceAddress) {
        this.retailPlaceAddress = retailPlaceAddress;
    }
}
