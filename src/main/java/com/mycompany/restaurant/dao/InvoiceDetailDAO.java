/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dao;


import com.mycompany.restaurant.dto.InvoiceDetailViewDTO;
import java.sql.*;
import java.util.*;


public class InvoiceDetailDAO extends DAO {

    public boolean addDetail(List<InvoiceDetailViewDTO> lines) {
        if (lines == null || lines.isEmpty()) return true;

        String sql = "INSERT INTO InvoiceDetail (invoice_id, ingredientSup_id, quantity) VALUES (?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            for (InvoiceDetailViewDTO d : lines) {
                ps.setInt(1, d.getInvoiceId());
                ps.setInt(2, d.getIngredientSupId());
                ps.setFloat(3, d.getQuantity());
                ps.addBatch();
            }

            int[] arr = ps.executeBatch();
            return arr.length == lines.size(); 
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
