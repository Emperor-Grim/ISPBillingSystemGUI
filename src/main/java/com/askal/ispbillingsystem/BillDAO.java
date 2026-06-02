package com.askal.ispbillingsystem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT b.*, CONCAT(c.firstName,' ',c.lastName) AS customerName " +
                     "FROM bills b JOIN customers c ON b.customerId = c.id " +
                     "ORDER BY b.dueDate DESC";
        try (Connection conn = DBConnection.connect()) {  // Fix #9: try-with-resources
            if (conn == null) return list;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate dueDate  = rs.getDate("dueDate")  != null ? rs.getDate("dueDate").toLocalDate()  : null;
                    LocalDate paidDate = rs.getDate("paidDate") != null ? rs.getDate("paidDate").toLocalDate() : null;
                    list.add(new Bill(
                        rs.getInt("id"), rs.getInt("customerId"),
                        rs.getString("customerName"), rs.getString("period"),
                        rs.getDouble("amount"), dueDate, paidDate, rs.getString("status")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean addBill(Bill b) {
        String sql = "INSERT INTO bills(customerId, period, amount, dueDate, status) VALUES(?,?,?,?,?)";
        String upd = "UPDATE customers SET balance = balance + ?, status='Unpaid' WHERE id=?";
        try (Connection conn = DBConnection.connect()) {  // Fix #9
            if (conn == null) return false;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, b.getCustomerId());
                stmt.setString(2, b.getPeriod());
                stmt.setDouble(3, b.getAmount());
                stmt.setDate(4, Date.valueOf(b.getDueDate()));
                stmt.setString(5, "Unpaid");
                stmt.executeUpdate();
            }
            try (PreparedStatement u = conn.prepareStatement(upd)) {
                u.setDouble(1, b.getAmount());
                u.setInt(2, b.getCustomerId());
                u.executeUpdate();
            }
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean payBill(int billId) {
        try (Connection conn = DBConnection.connect()) {  // Fix #9
            if (conn == null) return false;
            int customerId; double amount;

            try (PreparedStatement get = conn.prepareStatement("SELECT * FROM bills WHERE id=?")) {
                get.setInt(1, billId);
                try (ResultSet rs = get.executeQuery()) {
                    if (!rs.next()) return false;
                    customerId = rs.getInt("customerId");
                    amount     = rs.getDouble("amount");
                }
            }
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE bills SET status='Paid', paidDate=? WHERE id=?")) {
                stmt.setDate(1, Date.valueOf(LocalDate.now()));
                stmt.setInt(2, billId);
                stmt.executeUpdate();
            }
            try (PreparedStatement u = conn.prepareStatement(
                    "UPDATE customers SET balance = balance - ? WHERE id=?")) {
                u.setDouble(1, amount);
                u.setInt(2, customerId);
                u.executeUpdate();
            }
            try (PreparedStatement c = conn.prepareStatement(
                    "UPDATE customers SET status='Paid' WHERE id=? AND balance <= 0")) {
                c.setInt(1, customerId);
                c.executeUpdate();
            }
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // Fix #7 + #9: reverses customer balance for unpaid bills, uses try-with-resources
    public boolean deleteBill(int billId) {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return false;
            int customerId; double amount; String status;

            try (PreparedStatement get = conn.prepareStatement("SELECT * FROM bills WHERE id=?")) {
                get.setInt(1, billId);
                try (ResultSet rs = get.executeQuery()) {
                    if (!rs.next()) return false;
                    customerId = rs.getInt("customerId");
                    amount     = rs.getDouble("amount");
                    status     = rs.getString("status");
                }
            }
            try (PreparedStatement del = conn.prepareStatement("DELETE FROM bills WHERE id=?")) {
                del.setInt(1, billId);
                del.executeUpdate();
            }
            if ("Unpaid".equals(status)) {
                try (PreparedStatement upd = conn.prepareStatement(
                        "UPDATE customers SET balance = balance - ? WHERE id=?")) {
                    upd.setDouble(1, amount);
                    upd.setInt(2, customerId);
                    upd.executeUpdate();
                }
                try (PreparedStatement chk = conn.prepareStatement(
                        "UPDATE customers SET status='Paid' WHERE id=? AND balance <= 0")) {
                    chk.setInt(1, customerId);
                    chk.executeUpdate();
                }
            }
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<Bill> getBillsByStatus(String status) {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT b.*, CONCAT(c.firstName,' ',c.lastName) AS customerName " +
                     "FROM bills b JOIN customers c ON b.customerId = c.id " +
                     "WHERE b.status=? ORDER BY b.paidDate DESC";
        try (Connection conn = DBConnection.connect()) {  // Fix #9
            if (conn == null) return list;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        LocalDate dueDate  = rs.getDate("dueDate")  != null ? rs.getDate("dueDate").toLocalDate()  : null;
                        LocalDate paidDate = rs.getDate("paidDate") != null ? rs.getDate("paidDate").toLocalDate() : null;
                        list.add(new Bill(
                            rs.getInt("id"), rs.getInt("customerId"),
                            rs.getString("customerName"), rs.getString("period"),
                            rs.getDouble("amount"), dueDate, paidDate, rs.getString("status")
                        ));
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public double getTotalCollected() {
        try (Connection conn = DBConnection.connect()) {  // Fix #9
            if (conn == null) return 0;
            try (ResultSet rs = conn.prepareStatement(
                    "SELECT SUM(amount) FROM bills WHERE status='Paid'").executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0;
            }
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int getOverdueBillsCount() {
        try (Connection conn = DBConnection.connect()) {  // Fix #9
            if (conn == null) return 0;
            try (ResultSet rs = conn.prepareStatement(
                    "SELECT COUNT(*) FROM bills WHERE status='Unpaid' AND dueDate < CURDATE()").executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }
}
