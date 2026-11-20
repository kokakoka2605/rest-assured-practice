package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** POJO для /users. Берём только нужные поля, остальные игнорируем. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String name;
    private String username;
    private String email;

    public User() {}

    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
}