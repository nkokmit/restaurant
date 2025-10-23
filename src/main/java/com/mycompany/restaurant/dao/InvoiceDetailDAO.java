/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dao;


import com.mycompany.restaurant.dto.InvoiceDetailViewDTO;
import java.sql.*;
import java.util.*;


public class InvoiceDetailDAO extends DAO {

  public void addLine(int invoiceId, int ingredientSupId, float qty, float unitPrice) throws SQLException {
    String sql = "INSERT INTO InvoiceDetail(invoice_id,ingredientSup_id,quantity,unitPrice) VALUES(?,?,?,?)";
    
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, invoiceId);
      ps.setInt(2, ingredientSupId);
      ps.setFloat(3, qty);
     
      ps.setFloat(4, unitPrice); 
      
      ps.executeUpdate();
    }
  }

  public void removeLine(int detailId) throws SQLException {
    try (PreparedStatement ps = con.prepareStatement("DELETE FROM InvoiceDetail WHERE id=?")) {
      ps.setInt(1, detailId); ps.executeUpdate();
    }
  }

  public List<InvoiceDetailViewDTO> listByInvoice(int invoiceId) {
    List<InvoiceDetailViewDTO> list = new ArrayList<>();

    String sql =
        "SELECT d.id AS detailId, " +
        "       ing.name AS ingredientName, " +
        "       d.quantity AS qty, " +
        "       d.unitPrice AS unitPrice, " +
        "       (d.quantity * d.unitPrice) AS lineTotal " +          // không còn cột line_total
        "FROM InvoiceDetail d " +
        "JOIN IngredientSup isup ON isup.id = d.ingredientSup_id " +
        "JOIN Ingredient ing ON ing.id = isup.ingredient_id " +
        "WHERE d.invoice_id = ? " +
        "ORDER BY d.id";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, invoiceId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                InvoiceDetailViewDTO v = new InvoiceDetailViewDTO();
                v.setDetailId(rs.getInt("detailId"));           // <-- dùng alias
                v.setIngredientName(rs.getString("ingredientName"));
                v.setQty(rs.getFloat("qty"));
                v.setUnitPrice(rs.getFloat("unitPrice"));
                v.setLineTotal(rs.getFloat("lineTotal"));
                list.add(v);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return list;
}



  public float sumTotal(int invoiceId) {
    String sql = "SELECT COALESCE(SUM(quanity*unitPrice),0) FROM InvoiceDetail WHERE invoice_id=?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, invoiceId);
      try (ResultSet rs = ps.executeQuery()) {

        rs.next(); return rs.getFloat(1); 
        
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
  }
}
