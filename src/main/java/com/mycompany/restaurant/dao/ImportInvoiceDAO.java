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

  public ImportInvoice insertConfirmed(int supId, int staffId) {
        String sql = "INSERT INTO ImportInvoice(status, staff_id, supplier_id, createdAt) " +
                     "OUTPUT INSERTED.id VALUES(1, ?, ?, GETDATE())";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ps.setInt(2, supId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ImportInvoice iv = new ImportInvoice();
                    iv.setId(rs.getInt(1));
                    iv.setStatus(1);
                    return iv;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
