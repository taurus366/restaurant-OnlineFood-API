package com.tournament.restaurant.data.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Foods")
public class Food extends BaseEntity{

    @Expose
    @Column
    private String name;
    @Expose
    @Column
    private String description;
    @Expose
    @Column(name = "image_url")
    private String imageUrl;
    @Expose
    @Column(name = "price")
    private double price;


    public Food() {

    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Relations -------------------------------
    @OneToMany(mappedBy = "food",cascade = CascadeType.ALL)
    private List<ShoppingCart> carts;

    @OneToMany(mappedBy = "food")
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<ShoppingCart> getCarts() {
        return carts;
    }

    public void setCarts(List<ShoppingCart> carts) {
        this.carts = carts;
    }

    // Relations -------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
