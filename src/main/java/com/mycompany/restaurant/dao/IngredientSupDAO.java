/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dao;


import com.mycompany.restaurant.dto.IngredientViewDTO;
import java.sql.*;
import java.util.*;

public class IngredientSupDAO extends DAO {

  public List<IngredientViewDTO> searchIngredientbyName(int supplierId, String q) {
    String sql =
        "SELECT isup.id AS ingredientSupId, i.id AS ingredientId, " +
        "       i.name, i.type, i.unit, isup.price " +
        "FROM IngredientSup isup " +
        "JOIN Ingredient i ON i.id = isup.ingredient_id " +
        "WHERE isup.supplier_id = ? " +
        "  AND (i.name LIKE ? + '%' ) " +
        "ORDER BY i.name";
    List<IngredientViewDTO> out = new ArrayList<>();
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, supplierId);
      ps.setString(2, (q==null?"":q.trim()) + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            IngredientViewDTO v = new IngredientViewDTO();
            v.setIngredientSupId(rs.getInt("ingredientSupId"));
            v.setIngredientId(rs.getInt("ingredientId"));
            v.setName(rs.getString("name"));
            v.setType(rs.getString("type"));
            v.setUnit(rs.getString("unit"));   
            v.setPrice(rs.getFloat("price")); 
            out.add(v);

        }
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
    return out;
  }

 public int addIngredientWithMapping(String name, String type, String unit,
                                      float price, int supplierId) throws SQLException {
    con.setAutoCommit(false);
    try {
        int ingredientId;

        try (PreparedStatement ps = con.prepareStatement(
            "SELECT ingredient_id FROM Ingredient WHERE name = ? AND type = ? AND unit = ?")) {
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setString(3, unit);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ingredientId = rs.getInt("ingredient_id");
                } else {
                    try (PreparedStatement psInsert = con.prepareStatement(
                        "INSERT INTO Ingredient(name,type,unit) VALUES(?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                        psInsert.setString(1, name);
                        psInsert.setString(2, type);
                        psInsert.setString(3, unit);
                        psInsert.executeUpdate();
                        try (ResultSet rsGeneratedKeys = psInsert.getGeneratedKeys()) {
                            rsGeneratedKeys.next();
                            ingredientId = rsGeneratedKeys.getInt(1);
                        }
                    }
                }
            }
        }
        int ingredientSupId;
        try (PreparedStatement ps = con.prepareStatement(
            "INSERT INTO IngredientSup(ingredient_id,supplier_id,price) VALUES(?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ingredientId);
            ps.setInt(2, supplierId);
            ps.setFloat(3, price);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next(); 
                ingredientSupId = rs.getInt(1);
            }
        }
        con.commit(); 
        return ingredientSupId;
    } catch (SQLException ex) { 
        con.rollback(); 
        throw ex; 
    } finally { 
        con.setAutoCommit(true); 
    }
}
}