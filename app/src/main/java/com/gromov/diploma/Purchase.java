package com.gromov.diploma;

import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Purchase {

    @PrimaryKey(autoGenerate = true) private int id;
    private int category = 0;  // Выбор категории товаров в покупке (продукты, спорт и т.д.)
    @ColumnInfo(name = "placeAddress")private String retailPlaceAddress = null; // Наименование магазина
    @ColumnInfo(name = "totalSum")private float ecashTotalSum = 0; //общая стоимость (высчитываем из товаров или задаем сами?)
    private ArrayList<Product> items; //Список покупок

    public float getTotalCost() {
        return ecashTotalSum;
    }

    public int getCategory() {
        return category;
    }

    public ArrayList<Product> getProducts() {
        return items;
    }

    public String getRetailPlaceAddress() {
        return retailPlaceAddress;
    }


    public String showPurchase() {
        String show = "";

        show += "Общая сумма покупки: " + (String.valueOf(this.getTotalCost()) + "\n")
                + "Магазин: " + this.getRetailPlaceAddress() + "\n"
                + "Товары: " + "\n";
        ArrayList arrayList = this.getProducts();
        for (int i = 0; i < this.getProducts().size(); i++) {
            show += "     " + (((Product) arrayList.get(i)).showProduct());
        }
        return show;
    }
}
