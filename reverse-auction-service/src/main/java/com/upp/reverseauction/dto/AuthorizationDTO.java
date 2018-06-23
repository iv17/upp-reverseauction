package com.upp.reverseauction.dto;

public class AuthorizationDTO {

    private long id;

    private String token;

    private boolean isPrivateUser;

    private String username;

    public AuthorizationDTO() {
    }

    public AuthorizationDTO(long id, String token, boolean isPrivateUser, String username) {
        this.id = id;
        this.token = token;
        this.isPrivateUser = isPrivateUser;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isPrivateUser() {
        return isPrivateUser;
    }

    public void setPrivateUser(boolean privateUser) {
        isPrivateUser = privateUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
