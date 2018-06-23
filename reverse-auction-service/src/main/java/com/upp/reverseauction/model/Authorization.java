package com.upp.reverseauction.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Authorization implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private PrivateUser user;

    @NotNull
    @Column(unique = true)
    private String token;

    @PrePersist
    private void generateToken() {
        this.token = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PrivateUser getUser() {
        return user;
    }

    public void setUser(PrivateUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authorization)) return false;

        Authorization that = (Authorization) o;

        return getUser().equals(that.getUser()) && getToken().equals(that.getToken());
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "id=" + id +
                ", user=" + user +
                ", token='" + token + '\'' +
                '}';
    }

}