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
import java.math.RoundingMode;
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

    public List<User> getPendingUsers() {
        return userDAO.getPendingUsers();

    }

    public User getUserInfo(int id) {
        return userDAO.getUserById(id);
    }

    public int getPendingCount() {
        return userDAO.getCountPending();
    }

    public void updateUserDenied(int userID) {
        userDAO.updateUserDenied(userID);
    }

    public UserStockInfo getUserStockInfo(int userId, int stockId) {
        return userDAO.getUserStockInfo(userId, stockId);
    }


    public void updateUserApproved(int userID) {
        userDAO.updateUserApproved(userID);
    }

    public void updateUserPending(int userId) {
        userDAO.updateUserPending(userId);
    }

    public Response buyStock(int stockId, int buyQuantity) throws SQLException {
        //error: 1. no user log in 2. no enough money 3. no eno
        User currentUser = userDAO.getUserById(CurrentUser.getCurrentUser().getId());
        Stock stock = stockDAO.getStockById(stockId);
        UserStockInfo userStockInfo = userDAO.getUserStockInfo(currentUser.getId(), stock.getId());
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

        int currentQuantity = userStockInfo != null ? userStockInfo.getQuantity() : 0;
        BigDecimal currentAverageCost = userStockInfo != null ? userStockInfo.getAverageCost() : BigDecimal.valueOf(0);

        int newQuantity = currentQuantity + buyQuantity;
        userDAO.updateStockQuantity(currentUser.getId(), stock.getId(), newQuantity);

        BigDecimal newAverageCost = currentAverageCost.multiply(BigDecimal.valueOf(currentQuantity))
                .add(stock.getPrice().multiply(BigDecimal.valueOf(buyQuantity)))
                .divide(BigDecimal.valueOf(currentQuantity + buyQuantity), 2, RoundingMode.HALF_UP);
        userDAO.updateAverageCost(currentUser.getId(), stock.getId(), newAverageCost);

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

    public Response sellStock(int stockId, int sellQuantity) throws SQLException {
        User currentUser = userDAO.getUserById(CurrentUser.getCurrentUser().getId());
        Stock stock = stockDAO.getStockById(stockId);
        UserStockInfo userStockInfo = userDAO.getUserStockInfo(currentUser.getId(), stock.getId());
        if (currentUser == null) {
            return new Response(false, "No user is currently logged in");
        }
        if (sellQuantity > stock.getAmount()) {
            return new Response(false, "Lack of stocks in account");
        }

        BigDecimal totalIncome = stock.getPrice().multiply(BigDecimal.valueOf(sellQuantity));
        BigDecimal newBalance = currentUser.getBalance().add(totalIncome);
        userDAO.updateBalance(currentUser.getId(), newBalance);

        int newQuantity = userStockInfo.getQuantity() - sellQuantity;
        userDAO.updateStockQuantity(currentUser.getId(), stock.getId(), newQuantity);

        BigDecimal currentAverageCost = userStockInfo == null ? BigDecimal.valueOf(0) : userStockInfo.getAverageCost();
        BigDecimal realizedProfit = currentUser.getRealizedProfit();
        BigDecimal profitFromSale = (stock.getPrice().subtract(currentAverageCost)).multiply(BigDecimal.valueOf(sellQuantity));
        BigDecimal newRealizedProfit = realizedProfit.add(profitFromSale);
        userDAO.updateRealizedProfit(currentUser.getId(), newRealizedProfit);

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
        return userDAO.getUserStockInfoByUserId(userId);
    }

//    public
}
