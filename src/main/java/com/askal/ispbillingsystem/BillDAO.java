package com.askal.ispbillingsystem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return list;
            String sql = "SELECT b.*, CONCAT(c.firstName,' ',c.lastName) AS customerName " +
                         "FROM bills b JOIN customers c ON b.customerId = c.id " +
                         "ORDER BY b.dueDate DESC";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                LocalDate dueDate  = rs.getDate("dueDate")  != null ? rs.getDate("dueDate").toLocalDate()  : null;
                LocalDate paidDate = rs.getDate("paidDate") != null ? rs.getDate("paidDate").toLocalDate() : null;
                list.add(new Bill(
                    rs.getInt("id"),
                    rs.getInt("customerId"),
                    rs.getString("customerName"),
                    rs.getString("period"),
                    rs.getDouble("amount"),
                    dueDate, paidDate,
                    rs.getString("status")
                ));
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Add a new bill
    public boolean addBill(Bill b) {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return false;
            String sql = "INSERT INTO bills(customerId, period, amount, dueDate, status) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, b.getCustomerId());
            stmt.setString(2, b.getPeriod());
            stmt.setDouble(3, b.getAmount());
            stmt.setDate(4, Date.valueOf(b.getDueDate()));
            stmt.setString(5, "Unpaid");
            stmt.executeUpdate();

            // also update customer balance and status
            String upd = "UPDATE customers SET balance = balance + ?, status='Unpaid' WHERE id=?";
            PreparedStatement u = conn.prepareStatement(upd);
            u.setDouble(1, b.getAmount());
            u.setInt(2, b.getCustomerId());
            u.executeUpdate();

            conn.close();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean payBill(int billId) {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return false;

            // get bill info first
            PreparedStatement get = conn.prepareStatement("SELECT * FROM bills WHERE id=?");
            get.setInt(1, billId);
            ResultSet rs = get.executeQuery();
            if (!rs.next()) { conn.close(); return false; }
            int customerId = rs.getInt("customerId");
            double amount  = rs.getDouble("amount");

            // mark bill paid
            String sql = "UPDATE bills SET status='Paid', paidDate=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, billId);
            stmt.executeUpdate();

            // reduce customer balance
            String upd = "UPDATE customers SET balance = balance - ? WHERE id=?";
            PreparedStatement u = conn.prepareStatement(upd);
            u.setDouble(1, amount);
            u.setInt(2, customerId);
            u.executeUpdate();

            // if balance <= 0 mark customer paid
            String check = "UPDATE customers SET status='Paid' WHERE id=? AND balance <= 0";
            PreparedStatement c = conn.prepareStatement(check);
            c.setInt(1, customerId);
            c.executeUpdate();

            conn.close();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // Delete a bill
    public boolean deleteBill(int billId) {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return false;
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM bills WHERE id=?");
            stmt.setInt(1, billId);
            stmt.executeUpdate();
            conn.close();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // Get bills by status (for receipts - Paid only)
    public List<Bill> getBillsByStatus(String status) {
        List<Bill> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return list;
            String sql = "SELECT b.*, CONCAT(c.firstName,' ',c.lastName) AS customerName " +
                         "FROM bills b JOIN customers c ON b.customerId = c.id " +
                         "WHERE b.status=? ORDER BY b.paidDate DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate dueDate  = rs.getDate("dueDate")  != null ? rs.getDate("dueDate").toLocalDate()  : null;
                LocalDate paidDate = rs.getDate("paidDate") != null ? rs.getDate("paidDate").toLocalDate() : null;
                list.add(new Bill(
                    rs.getInt("id"), rs.getInt("customerId"),
                    rs.getString("customerName"), rs.getString("period"),
                    rs.getDouble("amount"), dueDate, paidDate, rs.getString("status")
                ));
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Stats for reports
    public double getTotalCollected() {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return 0;
            ResultSet rs = conn.prepareStatement(
                "SELECT SUM(amount) FROM bills WHERE status='Paid'").executeQuery();
            double val = rs.next() ? rs.getDouble(1) : 0;
            conn.close(); return val;
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int getOverdueBillsCount() {
        try {
            Connection conn = DBConnection.connect();
            if (conn == null) return 0;
            ResultSet rs = conn.prepareStatement(
                "SELECT COUNT(*) FROM bills WHERE status='Unpaid' AND dueDate < CURDATE()").executeQuery();
            int val = rs.next() ? rs.getInt(1) : 0;
            conn.close(); return val;
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }
}