package model;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-16:18
 * @Description:
 */

import java.math.BigDecimal;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private Approved approved;
    private BigDecimal balance;

    public User(int id, String name, String email, String password, Role role, Approved approved, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.approved = approved;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Approved isApproved() {
        return approved;
    }

    public void setApproved(Approved approved) {
        this.approved = approved;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public enum Role {
        CUSTOMER,
        MANAGER;
    }

    public enum Approved {
        REGISTERED,
        PENDING,
        APPROVED,
        DECLINED;
    }
}
