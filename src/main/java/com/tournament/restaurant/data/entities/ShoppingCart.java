package com.tournament.restaurant.data.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Shopping_cart")
public class ShoppingCart extends BaseEntity{

    @Expose
    @ManyToOne(fetch = FetchType.EAGER)
    private Food food;

    @ManyToOne
    private User user;

    @Expose
    @Column
    private String comment;

    @Expose
    @Column(name = "food_count")
    private int foodCount;

//    @Expose
//    @Column(name = "order_date")
//    private Timestamp orderDate;

    public ShoppingCart() {
    }

//    public Timestamp getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(Timestamp orderDate) {
//        this.orderDate = orderDate;
//    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
