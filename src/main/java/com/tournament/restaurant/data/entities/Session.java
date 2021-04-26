package com.tournament.restaurant.data.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Expose
    @Column(name = "auth_token")
    private String authToken;
    @Column(name = "created_time")
    private Timestamp createdTime;

//    @ManyToOne(cascade = CascadeType.ALL)
//    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Session() {
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
