package com.gromov.diploma;

public class Product {

    private String name;
    private int sum;
    private double quantity;


    public String showProduct() {
        String string = String.format("%-35.30s%s%10s%n", name, '|', sum);
        return string;
    }


}
