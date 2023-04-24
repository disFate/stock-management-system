package model;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-15:25
 * @Description:
 */
// model/Transaction.java

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int userId;
    private int stockId;
    private Type type;
    private int quantity;
    private Double price;
    private LocalDateTime transactionDate;

    public Transaction(int id, int userId, int stockId, Type type, int quantity, double price, LocalDateTime transactionDate) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public enum Type {
        BUY, SELL;
    }
}
