package model;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:36
 * @Description:
 */
public class Stock {
    public Stock(){}
    public Stock(int id, String symbol, String name, double price, int amount) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    private int id;
    private String symbol;
    private String name;
    private double price;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
