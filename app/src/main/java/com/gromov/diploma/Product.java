package com.gromov.diploma;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Purchase.class,
        parentColumns = "id",
        childColumns = "ownerId"))
public class Product {

    @PrimaryKey(autoGenerate = true) private int id;
    private String name;
    private int sum;
    private double quantity;

    private int ownerId;// this ID points to a Purchase


    public String showProduct() {
        String string = String.format("%-35.30s%s%10s%n", name, '|', sum);
        return string;
    }


}
