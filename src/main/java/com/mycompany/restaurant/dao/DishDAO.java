/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dao;


import com.mycompany.restaurant.models.Dish;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishDAO extends DAO {
  public List<Dish> searchDishByName(String name) {
    String sql = "SELECT id,name,type,price,descr,sale FROM Dish WHERE name LIKE ? ORDER BY id";
    List<Dish> out = new ArrayList<>();
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, (name==null?"":name.trim()) + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Dish d = new Dish();
          d.setId(rs.getInt("id"));
          d.setName(rs.getString("name"));
          d.setType(rs.getString("type"));
          d.setPrice(rs.getFloat("price"));
          d.setDescr(rs.getString("descr"));
          d.setSale(rs.getFloat("sale"));
          out.add(d);
        }
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
    return out;
  }  
  public Dish getById(int id) {
    String sql = "SELECT id,name,type,price,descr,sale FROM Dish WHERE id=?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          Dish i = new Dish();
          i.setId(rs.getInt(1));
          i.setName(rs.getString(2));
          i.setType(rs.getString(3));
          i.setPrice(rs.getFloat(4));
          i.setDescr(rs.getString(5));
          i.setSale(rs.getFloat(6));
          return i;
        }
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
    return null;
  }
}
