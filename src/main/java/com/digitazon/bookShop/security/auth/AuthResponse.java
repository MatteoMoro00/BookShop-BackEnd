package com.digitazon.bookShop.security.auth;

public class AuthResponse {
    //valutare ritornare un User, per vedere se è admin
    private int id;
    private String username;
    private String accessToken;

    private boolean admin;

    public AuthResponse() {
    }

    public AuthResponse(int id, String username, String accessToken, boolean admin) {
        this.id = id;
        this.username = username;
        this.accessToken = accessToken;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
