package dao;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-15:24
 * @Description:
 */
import model.Transaction;
import model.User;

import java.util.List;

public interface ITransactionDAO {
    void addTransaction(Transaction transaction);
    List<Transaction> getTransactionsByUser(User user);
}
