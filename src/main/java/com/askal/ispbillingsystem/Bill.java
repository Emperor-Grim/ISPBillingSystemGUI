package com.askal.ispbillingsystem;

import java.time.LocalDate;

public class Bill {
    private int id;
    private int customerId;
    private String customerName;
    private String period;
    private double amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private String status;

    public Bill() {}

    public Bill(int id, int customerId, String customerName,
                String period, double amount,
                LocalDate dueDate, LocalDate paidDate, String status) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.period = period;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paidDate = paidDate;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}