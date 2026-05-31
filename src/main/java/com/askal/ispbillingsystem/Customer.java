package com.askal.ispbillingsystem;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String plan;
    private double balance;
    private String status;

    public Customer(int id, String firstName, String lastName, String address,
                    String plan, double balance, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.plan = plan;
        this.balance = balance;
        this.status = status;
    }

    public Customer() {}

    public void setId(int id)             { this.id = id; }
    public int getId()                    { return id; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getFirstName()               { return firstName; }

    public void setLastName(String lastName)   { this.lastName = lastName; }
    public String getLastName()                { return lastName; }

    public void setAddress(String address)     { this.address = address; }
    public String getAddress()                 { return address; }

    public void setPlan(String plan)           { this.plan = plan; }
    public String getPlan()                    { return plan; }

    public void setBalance(double balance)     { this.balance = balance; }
    public double getBalance()                 { return balance; }

    public void setStatus(String status)       { this.status = status; }
    public String getStatus()                  { return status; }

    public String getFullName() { return firstName + " " + lastName; }
}
