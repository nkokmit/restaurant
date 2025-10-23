// src/main/java/com/mycompany/restaurant/models/WarehouseStaff.java
package com.mycompany.restaurant.models;

import java.util.Date;

public class WarehouseStaff {
    private int id;
    private String username;
    private String password;
    private Date birth;
    private String name;
    private String addr;
    private String tel;
    private String email;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Date getBirth() { return birth; }
    public void setBirth(Date birth) { this.birth = birth; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddr() { return addr; }
    public void setAddr(String addr) { this.addr = addr; }
    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
