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

  public boolean addInvoice(ImportInvoice inv) {
        if (inv == null) return false;
        Supplier sup = inv.getSup();
        WarehouseStaff staff = inv.getStaff();
        if (sup == null || staff == null) return false;

        final String sql =
                "INSERT INTO ImportInvoice(status, staff_id, supplier_id, createdAt) " +
                "OUTPUT INSERTED.id VALUES(1, ?, ?, GETDATE())";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, staff.getId());
            ps.setInt(2, sup.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    inv.setId(rs.getInt(1));
                    inv.setStatus(1);
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
