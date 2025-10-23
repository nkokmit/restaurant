package com.mycompany.restaurant.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtils {
    static {
        try {
           
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
          
            throw new RuntimeException("Lỗi: Không tìm thấy SQL Server Driver!", e);
        }
    }
    private static final String URL ="jdbc:sqlserver://localhost\\IMPORT;databaseName=master;encrypt=false;trustServerCertificate=true;";

    

    private static final String USER = "appuser";          
    private static final String PASS = "StrongPass!123";   

    private DBUtils() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
