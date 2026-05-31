package com.askal.ispbillingsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    // ── Change these to match your MySQL setup ──────────────────────────────
    private static final String URL  = "jdbc:mysql://localhost:3306/customerdata";
    private static final String USER = "root";
    private static final String PASS = "0922";
    // ────────────────────────────────────────────────────────────────────────

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
