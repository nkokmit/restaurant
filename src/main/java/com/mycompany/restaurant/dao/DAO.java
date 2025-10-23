/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dao;

/**
 *
 * @author hungmit
 */
import java.sql.Connection;
import com.mycompany.restaurant.utils.DBUtils;

public abstract class DAO {
  protected final Connection con;
  public DAO() {
    try { this.con = DBUtils.getConnection(); }
    catch (Exception e) { throw new RuntimeException(e); }
  }
}