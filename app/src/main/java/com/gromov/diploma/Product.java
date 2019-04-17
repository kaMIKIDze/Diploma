package com.gromov.diploma;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "product",
        foreignKeys = {
                @ForeignKey(
                        entity = Purchase.class,
                        parentColumns = "id",
                        childColumns = "ownerId",
                        onDelete = CASCADE),
                @ForeignKey(entity = Category.class,
                        parentColumns = "id",
                        childColumns = "categoryId")//Что будет происходить при удалении категории из БД?
        })
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;
    private int sum;
    private double quantity;
    private int categoryId = 1;  // Выбор категории товаров в покупке (продукты, спорт и т.д.)

    @ColumnInfo(name = "ownerId")
    public long ownerId;// this ID points to a Purchase


    public String showProduct() {
        String string = String.format("%-35.30s%s%10s%n", name, '|', sum);
        return string;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public int getSum() {
        return sum;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

}
