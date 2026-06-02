package com.askal.ispbillingsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DBConnection.connect()) {  // Fix #9: try-with-resources throughout
            if (conn == null) return list;
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers");
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Customer(
                        rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("address"), rs.getString("plan"),
                        rs.getDouble("balance"), rs.getString("status"), rs.getInt("dueDay")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void showAllCustomers() {
        for (Customer c : getAllCustomers()) {
            System.out.println(c.getId() + " | " + c.getFullName() + " | " +
                c.getPlan() + " | " + c.getBalance() + " | " + c.getStatus());
        }
    }

    public boolean addCustomer(Customer c) {
        String sql = "INSERT INTO customers(firstName, lastName, address, plan, balance, status, dueDay) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return false;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, c.getFirstName());
                stmt.setString(2, c.getLastName());
                stmt.setString(3, c.getAddress());
                stmt.setString(4, c.getPlan());
                stmt.setDouble(5, c.getBalance());
                stmt.setString(6, c.getStatus());
                stmt.setInt(7, c.getDueDay());
                stmt.executeUpdate();
            }
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean updateCustomer(Customer c) {
        String sql = "UPDATE customers SET firstName=?, lastName=?, address=?, plan=?, balance=?, status=?, dueDay=? WHERE id=?";
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return false;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, c.getFirstName());
                stmt.setString(2, c.getLastName());
                stmt.setString(3, c.getAddress());
                stmt.setString(4, c.getPlan());
                stmt.setDouble(5, c.getBalance());
                stmt.setString(6, c.getStatus());
                stmt.setInt(7, c.getDueDay());
                stmt.setInt(8, c.getId());
                return stmt.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean deleteCustomer(int id) {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return false;
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM customers WHERE id = ?")) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // Fix #8: also marks all unpaid bills as paid when marking customer paid
    public boolean markAsPaid(int id) {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return false;
            int rows;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE customers SET balance=0, status='Paid' WHERE id=?")) {
                stmt.setInt(1, id);
                rows = stmt.executeUpdate();
            }
            try (PreparedStatement sync = conn.prepareStatement(
                    "UPDATE bills SET status='Paid', paidDate=? WHERE customerId=? AND status='Unpaid'")) {
                sync.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
                sync.setInt(2, id);
                sync.executeUpdate();
            }
            return rows > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public int getTotalCustomers() {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return 0;
            try (ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM customers").executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int getPaidCount() {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return 0;
            try (ResultSet rs = conn.prepareStatement(
                    "SELECT COUNT(*) FROM customers WHERE status='Paid'").executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int getUnpaidCount() {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return 0;
            try (ResultSet rs = conn.prepareStatement(
                    "SELECT COUNT(*) FROM customers WHERE status != 'Paid'").executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public double getTotalBalance() {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return 0;
            try (ResultSet rs = conn.prepareStatement(
                    "SELECT SUM(balance) FROM customers").executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0;
            }
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public Customer getCustomerById(int id) {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return null;
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers WHERE id = ?")) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Customer(
                            rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"),
                            rs.getString("address"), rs.getString("plan"),
                            rs.getDouble("balance"), rs.getString("status"), rs.getInt("dueDay")
                        );
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
