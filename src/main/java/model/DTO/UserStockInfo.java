package model.DTO;

import java.math.BigDecimal;

/**
 * @Author: Tsuna
 * @Date: 2023-04-27-16:25
 * @Description:
 */
public class UserStockInfo {
    private int userId;
    private int stockId;
    private String stockSymbol;
    private String stockName;
    private BigDecimal averageCost;
    private BigDecimal price;
    private int quantity;
    private BigDecimal unrealizedProfit;

    // Add constructors, getters and setters here

    public UserStockInfo(int userId, int stockId, String stockSymbol, String stockName, BigDecimal averageCost, int quantity, BigDecimal unrealizedProfit, BigDecimal price) {
        this.userId = userId;
        this.stockId = stockId;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.averageCost = averageCost;
        this.quantity = quantity;
        this.unrealizedProfit = unrealizedProfit;
        this.price = price;
    }

    public UserStockInfo() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public BigDecimal getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(BigDecimal averageCost) {
        this.averageCost = averageCost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnrealizedProfit() {
        return unrealizedProfit;
    }

    public void setUnrealizedProfit(BigDecimal unrealizedProfit) {
        this.unrealizedProfit = unrealizedProfit;
    }

    @Override
    public String toString() {
        return "UserStockInfo{" +
                "userId=" + userId +
                ", stockId=" + stockId +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", stockName='" + stockName + '\'' +
                ", averageCost=" + averageCost +
                ", quantity=" + quantity +
                ", unrealizedProfit=" + unrealizedProfit +
                '}';
    }
}
