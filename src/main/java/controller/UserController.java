package controller;

import common.Response;
import dao.IStockDAO;
import dao.ITransactionDAO;
import dao.IUserDAO;
import model.DTO.UserStockInfo;
import model.Entity.Stock;
import model.Entity.Transaction;
import model.Entity.User;
import session.CurrentUser;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UserController {
    IUserDAO userDAO;
    ITransactionDAO transactionDAO;
    IStockDAO stockDAO;

    public UserController(IUserDAO userDAO, ITransactionDAO transactionDAO, IStockDAO stockDAO) {
        this.userDAO = userDAO;
        this.transactionDAO = transactionDAO;
        this.stockDAO = stockDAO;
    }

    public List<User> getRegisteredUsers() {
        return userDAO.getRegisteredUsers();
    }


    public Response buyStock(Stock stock, int buyQuantity) throws SQLException {
        //error: 1. no user log in 2. no enough money 3. no eno
        User currentUser = userDAO.getUserById(CurrentUser.getCurrentUser().getId());
        if (currentUser == null) {
            return new Response(false, "No user is currently logged in");
        }
        BigDecimal totalCost = stock.getPrice().multiply(BigDecimal.valueOf(buyQuantity));
        if (currentUser.getBalance().compareTo(totalCost) < 0) {
            return new Response(false, "Lack of money");
        }
        if (buyQuantity > stock.getAmount()) {
            return new Response(false, "only " + stock.getAmount() + " left in market");
        }
        BigDecimal newBalance = currentUser.getBalance().subtract(totalCost);

        userDAO.updateBalance(currentUser.getId(), newBalance);
        userDAO.updateStockQuantity(currentUser.getId(), stock.getId(), buyQuantity, Transaction.Type.BUY);
        userDAO.updateAverageCost(currentUser.getId(), stock.getId(), buyQuantity, stock.getPrice());

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
        User currentUser = userDAO.getUserById(CurrentUser.getCurrentUser().getId());
        if (currentUser == null) {
            return new Response(false, "No user is currently logged in");
        }
        if (sellQuantity > stock.getAmount()) {
            return new Response(false, "Lack of stocks in account");
        }

        BigDecimal totalIncome = stock.getPrice().multiply(BigDecimal.valueOf(sellQuantity));
        BigDecimal newBalance = currentUser.getBalance().add(totalIncome);

        userDAO.updateBalance(currentUser.getId(), newBalance);
        userDAO.updateStockQuantity(currentUser.getId(), stock.getId(), sellQuantity, Transaction.Type.SELL);
        userDAO.updateRealizedProfit(currentUser.getId(), stock.getId(), sellQuantity, stock.getPrice());

        stockDAO.updateAmount(stock.getId(), sellQuantity, Transaction.Type.SELL);

        transactionDAO.addTransaction(new Transaction(0,
                CurrentUser.getCurrentUser().getId(),
                stock.getId(),
                Transaction.Type.SELL,
                sellQuantity,
                stock.getPrice(),
                LocalDateTime.now())
        );
        return new Response(true, "sold successfully");
    }

    public List<UserStockInfo> getUnrealizedProfit(int userId) {
        return userDAO.getUserStockInfo(userId);
    }

//    public
}
