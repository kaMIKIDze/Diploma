package com.gromov.diploma.view.analysis;

public class StatisticsByCategory {
    private String name;
    private float cost;

    public StatisticsByCategory(String name) {
        this.name = name;
        cost = 0;
    }

    public void addCost(float cost) {
        this.cost += cost;
    }

    public float getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
