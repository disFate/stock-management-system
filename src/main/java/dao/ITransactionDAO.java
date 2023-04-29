package dao;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-15:24
 * @Description:
 */

import model.Entity.Transaction;

import java.util.List;

public interface ITransactionDAO {
    void addTransaction(Transaction transaction);

    List<Transaction> getTransactionsByUserId(int userId);
}
