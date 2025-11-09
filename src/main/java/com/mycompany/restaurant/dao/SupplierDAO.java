/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dao;


import com.mycompany.restaurant.models.Supplier;
import java.sql.*;
import java.util.*;

public class SupplierDAO extends DAO {

  public boolean addSupplier(Supplier u) {
    String sql = "INSERT INTO Supplier(name, addr, tel, email) VALUES(?,?,?,?)";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, u.getName());
      ps.setString(2, u.getAddr());
      ps.setString(3, u.getTel());
      ps.setString(4, u.getEmail());
      ps.executeUpdate();
      return true;
    }catch(SQLException e)
    {
       e.printStackTrace();
       return false;
    }
    
  }

  public List<Supplier> searchSupplierByName(String name) {
    String sql = "SELECT id,name,addr,tel,email FROM Supplier WHERE name LIKE ? ORDER BY id";
    List<Supplier> out = new ArrayList<>();
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1,("%"+(name==null?"":name.trim())) + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Supplier s = new Supplier();
          s.setId(rs.getInt("id"));
          s.setName(rs.getString("name"));
          s.setAddr(rs.getString("addr"));
          s.setTel(rs.getString("tel"));
          s.setEmail(rs.getString("email"));
          out.add(s);
        }
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
    return out;
  }

  
}
