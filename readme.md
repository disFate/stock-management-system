## file
StockManagementSystem  
├── controllers  
│   ├── LoginController.java  
│   ├── RegisterController.java  
│   ├── PortfolioController.java  
│   ├── StockController.java  
│   ├── TradeController.java  
│   └── UserController.java  
├── models  
│   ├── User.java  
│   ├── Portfolio.java  
│   ├── Stock.java  
│   ├── Trade.java  
│   └── Transaction.java  
├── views  
│   ├── LoginView.java  
│   ├── RegisterView.java  
│   ├── PortfolioView.java  
│   ├── StockView.java  
│   ├── TradeView.java  
│   └── UserView.java  
├── utils  
│   ├── DBUtil.java  
│   └── StockAPI.java  
└── StockManagementSystem.java  

## schema
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('customer') NOT NULL,
    approved BOOLEAN NOT NULL DEFAULT FALSE,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0
);

CREATE TABLE stocks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    symbol VARCHAR(10) NOT NULL UNIQUE,
    company_name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE user_stocks (
    user_id INT,
    stock_id INT,
    quantity INT NOT NULL,
    PRIMARY KEY (user_id, stock_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (stock_id) REFERENCES stocks(id)
);

CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    stock_id INT NOT NULL,
    type ENUM('buy', 'sell') NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (stock_id) REFERENCES stocks(id)
);
```
