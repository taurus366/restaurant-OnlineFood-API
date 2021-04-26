package com.tournament.restaurant.data.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "confirm_order")
public class ConfirmedOrder extends BaseEntity{

    @Expose
    @Column(name = "food_name")
    private String foodName;

    @Expose
    @Column(name = "confirm_date")
    private String confirmDate;

    @Expose
    private double price;

    @Expose
    private int foodCount;

    public ConfirmedOrder() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }



}
