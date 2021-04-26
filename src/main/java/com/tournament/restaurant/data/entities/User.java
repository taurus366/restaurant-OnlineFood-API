package com.tournament.restaurant.data.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Expose
    @Column
    private String username;

    @Expose(serialize = false)
    @Column
    private String password;

    @Expose
    @Column
    private String address;
    @Expose
    @Column(name = "full_name")
    private String fullName;

    @Expose
    @Column(name = "phone_number")
    private String phoneNumber;

    @Expose
    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    public User(){

    }

    // Relations -------------------------------
//    @Expose
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private List<Session> sessions;

//    public List<Session> getSessions() {
//        return sessions;
//    }
//
//    public void setSessions(List<Session> sessions) {
//        this.sessions = sessions;
//    }
    @Expose
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Session session;

    @Expose
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<ShoppingCart> shoppingCarts;

    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }
// Relations -------------------------------


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
