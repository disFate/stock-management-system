package controller;

import common.Response;
import dao.IStockDAO;
import dao.ITransactionDAO;
import dao.IUserDAO;
import model.Stock;
import model.Transaction;
import model.User;
import session.CurrentUser;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserController {
    IUserDAO userDAO;
    ITransactionDAO transactionDAO;
    IStockDAO stockDAO;

    public UserController(IUserDAO userDAO, ITransactionDAO transactionDAO, IStockDAO stockDAO) {
        this.userDAO = userDAO;
        this.transactionDAO = transactionDAO;
        this.stockDAO = stockDAO;
    }

    public Response buyStock(Stock stock, int buyQuantity) throws SQLException {
        //error: 1. no user log in 2. no enough money 3. no eno
        User currentUser = new CurrentUser().getCurrentUser();
        if (currentUser == null) {
            return new Response(false, "No user is currently logged in");
        }
        BigDecimal totalCost = BigDecimal.valueOf(stock.getPrice() * buyQuantity);
        if (currentUser.getBalance().compareTo(totalCost) < 0) {
            return new Response(false, "Lack of money");
        }
        if (buyQuantity > stock.getAmount()) {
            return new Response(false, "only " + stock.getAmount() + " left in market");
        }
        currentUser.setBalance(currentUser.getBalance().subtract(totalCost));
        stock.setAmount(stock.getAmount() - buyQuantity);
        userDAO.updateBalance(currentUser.getId(), currentUser);
        userDAO.updateStock(currentUser.getId(), stock.getId(), buyQuantity, Transaction.Type.BUY);
        stockDAO.updateAmount(stock.getId(), buyQuantity, Transaction.Type.BUY);
        transactionDAO.addTransaction(new Transaction(
                0,
                currentUser.getId(),
                stock.getId(),
                Transaction.Type.BUY,
                buyQuantity,
                stock.getPrice(),
                LocalDateTime.now()
        ));
        return new Response(true, "bought successfully");
    }

    public Response sellStock(Stock stock, int sellQuantity) throws SQLException {
        User currentUser = new CurrentUser().getCurrentUser();
        if (currentUser == null) {
            return new Response(false, "No user is currently logged in");
        }
        if (sellQuantity > stock.getAmount()) {
            return new Response(false, "Lack of stocks in account");
        }

        BigDecimal totalIncome = BigDecimal.valueOf(stock.getPrice() * sellQuantity);
        currentUser.setBalance(currentUser.getBalance().add(totalIncome));
        stock.setAmount(stock.getAmount() - sellQuantity);
        userDAO.updateStock(currentUser.getId(), stock.getId(), sellQuantity, Transaction.Type.SELL);
        stockDAO.updateAmount(stock.getId(), sellQuantity, Transaction.Type.SELL);
        transactionDAO.addTransaction(new Transaction(0,
                new CurrentUser().getCurrentUser().getId(),
                stock.getId(),
                Transaction.Type.SELL,
                sellQuantity,
                stock.getPrice(),
                LocalDateTime.now())
        );
        return new Response(true, "sold successfully");
    }
}
