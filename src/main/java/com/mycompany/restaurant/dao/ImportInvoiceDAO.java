/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dao;


import com.mycompany.restaurant.models.ImportInvoice;
import com.mycompany.restaurant.models.Supplier;
import com.mycompany.restaurant.models.WarehouseStaff;
import java.sql.*;
import java.util.Date;

public class ImportInvoiceDAO extends DAO {

  public ImportInvoice createDraft(int supplierId, int staffId) throws SQLException {
    String sql = "INSERT INTO ImportInvoice(createdAt,status,staff_id,supplier_id) VALUES(?,?,?,?)";
    try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
      ps.setInt(2, 0);
      ps.setInt(3, staffId);
      ps.setInt(4, supplierId);
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          ImportInvoice iv = new ImportInvoice();
          iv.setId(rs.getInt(1));
          iv.setDateIn(new Date());
          iv.setStatus(0);
          WarehouseStaff w = new WarehouseStaff(); w.setId(staffId); iv.setStaff(w);
          Supplier s = new Supplier(); s.setId(supplierId); iv.setSup(s);
          return iv;
        }
      }
    }
    return null;
  }

  public ImportInvoice getById(int id) {
    String sql = "SELECT id,createdAt,status,staff_id,supplier_id FROM ImportInvoice WHERE id=?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          ImportInvoice iv = new ImportInvoice();
          iv.setId(rs.getInt(1));
          iv.setDateIn(rs.getTimestamp(2));
          iv.setStatus(rs.getInt(3));
          WarehouseStaff w = new WarehouseStaff(); w.setId(rs.getInt(4)); iv.setStaff(w);
          Supplier s = new Supplier(); s.setId(rs.getInt(5)); iv.setSup(s);
          return iv;
        }
      }
    } catch (SQLException e) { throw new RuntimeException(e); }
    return null;
  }

  public void commit(int invoiceId) throws SQLException {
    String sql = "UPDATE ImportInvoice SET status=1 WHERE id=? AND status=0";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, invoiceId);
      ps.executeUpdate();
    }
  }
}
