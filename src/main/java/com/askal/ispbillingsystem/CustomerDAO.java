package com.askal.ispbillingsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // ── Return all customers as a List (used by Swing table) ────────────────
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return list;
            String sql = "SELECT * FROM customers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Customer(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("address"),
                    rs.getString("plan"),
                    rs.getDouble("balance"),
                    rs.getString("status")
                ));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ── Original console print (kept for compatibility) ─────────────────────
    public void showAllCustomers() {
        for (Customer c : getAllCustomers()) {
            System.out.println(c.getId() + " | " + c.getFullName() + " | " +
                c.getPlan() + " | " + c.getBalance() + " | " + c.getStatus());
        }
    }

    // ── Add customer ─────────────────────────────────────────────────────────
    public boolean addCustomer(Customer c) {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return false;
            String sql = "INSERT INTO customers(firstName, lastName, address, plan, balance, status) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getFirstName());
            stmt.setString(2, c.getLastName());
            stmt.setString(3, c.getAddress());
            stmt.setString(4, c.getPlan());
            stmt.setDouble(5, c.getBalance());
            stmt.setString(6, c.getStatus());
            stmt.executeUpdate();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── Update customer ──────────────────────────────────────────────────────
    public boolean updateCustomer(Customer c) {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return false;
            String sql = "UPDATE customers SET firstName=?, lastName=?, address=?, plan=?, balance=?, status=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getFirstName());
            stmt.setString(2, c.getLastName());
            stmt.setString(3, c.getAddress());
            stmt.setString(4, c.getPlan());
            stmt.setDouble(5, c.getBalance());
            stmt.setString(6, c.getStatus());
            stmt.setInt(7, c.getId());
            int rows = stmt.executeUpdate();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── Delete customer ──────────────────────────────────────────────────────
    public boolean deleteCustomer(int id) {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return false;
            String sql = "DELETE FROM customers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── Mark customer as paid ────────────────────────────────────────────────
    public boolean markAsPaid(int id) {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return false;
            String sql = "UPDATE customers SET balance=0, status='Paid' WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── Stats helpers for dashboard ──────────────────────────────────────────
    public int getTotalCustomers() {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return 0;
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM customers").executeQuery();
            int count = rs.next() ? rs.getInt(1) : 0;
            conn.close();
            return count;
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int getPaidCount() {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return 0;
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM customers WHERE status='Paid'").executeQuery();
            int count = rs.next() ? rs.getInt(1) : 0;
            conn.close();
            return count;
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int getUnpaidCount() {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return 0;
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM customers WHERE status != 'Paid'").executeQuery();
            int count = rs.next() ? rs.getInt(1) : 0;
            conn.close();
            return count;
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public double getTotalBalance() {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return 0;
            ResultSet rs = conn.prepareStatement("SELECT SUM(balance) FROM customers").executeQuery();
            double total = rs.next() ? rs.getDouble(1) : 0;
            conn.close();
            return total;
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }
}
