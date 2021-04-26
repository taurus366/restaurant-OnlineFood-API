package com.tournament.restaurant.data.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Expose
    @Column(name = "order_date")
    private String orderDate;

    @Expose
    @Column(name = "food_count")
    private int foodCount;

    @ManyToOne
    private User user;

    @Expose
    @ManyToOne
    private Food food;

    @Expose
    private String comment;

    @Expose
    @Column(name = "quantity_price")
    private double price;

    @Expose
    @Column(name = "address")
    private String address;

    @Expose
    @Column(name = "full_name")
    private String fullName;

    @Expose
    @Column(name = "phone_number")
    private String phoneNumber;

    public Order() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
