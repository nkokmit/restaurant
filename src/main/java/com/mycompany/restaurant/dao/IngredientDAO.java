/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dao;


import com.mycompany.restaurant.models.Ingredient;
import java.sql.*;

public class IngredientDAO extends DAO {
  public Ingredient getById(int id) {
    String sql = "SELECT id,name,type,unit FROM Ingredient WHERE id=?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          Ingredient i = new Ingredient();
          i.setId(rs.getInt(1));
          i.setName(rs.getString(2));
          i.setType(rs.getString(3));
          i.setUnit(rs.getString(4));
          return i;
        }
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
    return null;
  }
}
