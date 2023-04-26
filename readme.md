# Stock Portfolio Management System

This project is a Stock Portfolio Management system, which consists of two major subsystems: a portfolio management system and a customer stock trading system. The system is developed using Java Swing and follows the MVC architecture.

## schema
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('customer', 'manager') NOT NULL,
    approved ENUM('registered', 'pending', 'approved', 'declined') NOT NULL,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0
);

CREATE TABLE stocks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    symbol VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    amount INT NOT NULL
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

## File Structure

```
src
├── main
│   ├── controller
│   │   ├── CurrentUser.java
│   │   └── StockController.java
│   ├── dao
│   │   ├── DatabaseConfig.java
│   │   ├── IStockDAO.java
│   │   └── impl
│   │       └── StockDAOImpl.java
│   ├── model
│   │   ├── Stock.java
│   │   └── User.java
│   ├── session
│   │   └── CurrentUser.java
│   └── view
│       ├── managerPages
│           ├── addEdotDeleteStocks.java
│           ├── ManagerFirstPage.java
│           └── UserNotifyPage.java
│           └── UserRequestsPage.java
│       └── userPages
│           ├── StockDisplayPage.java
│           ├── UserMenuPage.java
│           └── UserStockPage.java

└── resources
    └── database.properties
```

### Directories and Files

- `controller`: Contains the controllers for the application, which are responsible for handling user interactions and managing the data flow between the view and the model.
    - `CurrentUser.java`: Singleton class to store the currently logged-in user's information.
    - `StockController.java`: Controller class for managing stocks and user stocks.
- `dao`: Contains the data access objects for accessing and manipulating data in the database.
    - `DatabaseConfig.java`: Singleton class to read and store database configuration from the properties file.
    - `IStockDAO.java`: Interface defining the operations for interacting with the stocks and user stocks data.
    - `impl`: Contains the implementation of the data access objects.
        - `StockDAOImpl.java`: Implementation of the `IStockDAO` interface.
- `model`: Contains the data models for the application.
    - `Stock.java`: Represents a stock.
    - `User.java`: Represents a user.
- `view`: Contains the user interface classes for the application.
    - `LoginPage.java`: Login page for the application.
    - `StockPage.java`: Displays all available stocks and allows users to buy stocks.
    - `UserStockPage.java`: Displays the logged-in user's stocks and allows users to sell stocks.
- `resources`: Contains the resource files for the application.
    - `database.properties`: Stores the database configuration, including URL, driver, username, and password.

## Implemented Features

- Display all available stocks in `StockPage`
- Display user-owned stocks in `UserStockPage`
- Buy stocks from `StockPage`, Sell stocks from `UserStockPage`, refresh page after transaction(but can check in database)
- Select options from 'UserMenuPage', jump to other pages by clicking button

## Not Implemented Features
- user login and record status in Session
- user register

## Notes for Team Members

Please make sure to update the `database.properties` file with your local or cloud environment and remember don't push
it to GitHub
