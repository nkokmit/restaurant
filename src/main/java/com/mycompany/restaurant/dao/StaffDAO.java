// src/main/java/com/mycompany/restaurant/dao/StaffDAO.java
package com.mycompany.restaurant.dao;

import com.mycompany.restaurant.models.WarehouseStaff;
import com.mycompany.restaurant.utils.DBUtils;

import java.sql.*;
import java.util.Date;

public class StaffDAO extends DAO {

    public StaffDAO() { super(); }

    public WarehouseStaff findByUsernamePassword(String username, String password) {
        String sql = "SELECT id, username, password, birth, name, addr, tel, email " +
                     "FROM WarehouseStaff WHERE username=? AND password=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    WarehouseStaff s = new WarehouseStaff();
                    s.setId(rs.getInt("id"));
                    s.setUsername(rs.getString("username"));
                    s.setPassword(rs.getString("password"));
                    Timestamp b = rs.getTimestamp("birth");
                    s.setBirth(b != null ? new Date(b.getTime()) : null);
                    s.setName(rs.getString("name"));
                    s.setAddr(rs.getString("addr"));
                    s.setTel(rs.getString("tel"));
                    s.setEmail(rs.getString("email"));
                    return s;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
