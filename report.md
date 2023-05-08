# Stock Portfolio Management System

This project is a Stock Portfolio Management system, which consists of two major subsystems: a portfolio management
system and a customer stock trading system. The system is developed using Java Swing and follows the MVC architecture.

## schema

```sql
CREATE TABLE users
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(255)        NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    password        VARCHAR(255)        NOT NULL,
    role            ENUM('customer', 'manager') NOT NULL,
    approved        ENUM('registered', 'pending', 'approved', 'declined') NOT NULL,
    balance         DECIMAL(10, 2)      NOT NULL DEFAULT 0,
    realized_profit DECIMAL(10, 2)      NOT NULL DEFAULT 0
);

CREATE TABLE stocks
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    symbol    VARCHAR(10)    NOT NULL UNIQUE,
    name      VARCHAR(255)   NOT NULL,
    isDeleted BOOLEAN        NOT NULL DEFAULT FALSE,
    price     DECIMAL(10, 2) NOT NULL,
    amount    INT            NOT NULL
);

CREATE TABLE user_stocks
(
    user_id      INT,
    stock_id     INT,
    quantity     INT            NOT NULL,
    average_cost DECIMAL(10, 2) NOT NULL DEFAULT 0,
    PRIMARY KEY (user_id, stock_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (stock_id) REFERENCES stocks (id)
);

CREATE TABLE transactions
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    user_id          INT            NOT NULL,
    stock_id         INT            NOT NULL,
    type             ENUM('buy', 'sell') NOT NULL,
    quantity         INT            NOT NULL,
    price            DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (stock_id) REFERENCES stocks (id)
);

CREATE TABLE message
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    user_id      INT      NOT NULL,
    message      TEXT     NOT NULL,
    isRead       BOOLEAN  NOT NULL DEFAULT FALSE,
    message_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
```

## File Structure

```
src
├── main
│   ├── common
│   │   └── Response
│   ├── controller
│   │   ├── MessageController.java
│   │   ├── UserController.java
│   │   └── StockController.java
│   ├── dao
│   │   ├── IMessageDAO
│   │   ├── IStockDAO
│   │   ├── ITransactionDAO
│   │   ├── IUserDAO.java
│   │   └── impl
│   │       ├── MessageDAOImpl.java
│   │       ├── StockDAOImpl.java
│   │       ├── TransactionDAOImpl.java
│   │       └── UserDAOImpl.java
│   ├── DataSource
│   │   ├── DatabaseConfig
│   │   └── DatabaseConnection.java
│   ├── model
│   │   ├── DTO
│   │   │   └── UserStockInfo.java
│   │   └── Entity
│   │       ├── Message.java
│   │       ├── Stock.java
│   │       ├── Transaction.java
│   │       └── User.java
│   ├── session
│   │   └── CurrentUser.java
│   └── view
│       ├── managerPages
│       │   ├── addEdotDeleteStocks.java
│       │   ├── ManagerFirstPage.java
│       │   ├── UserNotifyPage.java
│       │   └── UserRequestsPage.java
│       ├── userPages
│       │   ├── StockDisplayPage.java
│       │   ├── ManageAccountPage.java
│       │   ├── UserMenuPage.java
│       │   └── UserStockPage.java
│       ├── CustomerLogin.java 
│       ├── HomePage.java  
│       ├── ManagerLogin.java  
│       ├── SignUp.java  
│       └── WelcomePanel.java
└── resources
    └── database.properties
```

## Design Pattern

Our application follows the **Model-View-Controller (MVC)** architectural pattern, which helps us separate the concerns
of data management, user interface, and control flow. The following is a brief description of the design architecture
and the components involved in our system.

### 📦 Model

The **Model** components include the Data Access Objects (DAO) interfaces and their implementations, and the Entity
classes representing the domain objects in our system. The Model is responsible for managing the data and the business
logic of the application.

- 📝 **Entity**: These classes represent the main domain objects in our system, such as User, Stock, Transaction, and
  Message.
- 💾 **DAO**: The DAO interfaces and their implementations (in the "impl" package) handle the data access and storage
  logic for the respective domain objects.

### 🖥️ View

The **View** components consist of Java Swing panels that make up the user interface of the application. The View is
responsible for displaying the data to the user and receiving user inputs.

- 📋 **managerPages**: These panels represent the various pages in the Manager's user interface, including
  Add/Edit/Delete Stocks, Manager First Page, User Notify Page, and User Requests Page.
- 📋 **userPages**: These panels represent the pages in the User's user interface, such as Stock Display Page, Manage
  Account Page, User Menu Page, and User Stock Page.
- 📋 **Other panels** include CustomerLogin, HomePage, ManagerLogin, SignUp, and WelcomePanel, which are responsible for
  user authentication, registration, and displaying the main application interface.

### 🕹️ Controller

The **Controller** components are responsible for handling user input and managing the flow of data between the Model
and the View.

- UserController, MessageController, and StockController are responsible for managing user interactions, message
  handling, and stock data, respectively.

### 🔧 Common

The **common** package contains utility classes like Response, which are used across the application.

### 🌐 DataSource

This package contains classes related to database configuration and connection management, such as DatabaseConfig and
DatabaseConnection.

### 🚀 Session

The **session** package includes the CurrentUser class, which stores the current user's session information.

### 📁 Resources

This directory contains the "database.properties" file, which holds the database configuration settings.

Overall, the application is structured in a modular fashion, which helps to keep the code organized, maintainable, and
easy to understand.

## Benefits

Our system offers several benefits, making it an ideal choice for managing user and stock data. Here are some of the key
advantages of our application:

1. **MVC Architecture**: By adopting the Model-View-Controller (MVC) architectural pattern, our system is designed to be
   easy to extend and maintain. This pattern separates the concerns of data management, user interface, and control
   flow, allowing for better organization of the codebase. As a result, adding new features or modifying existing ones
   becomes a more manageable task, as the impact of changes is limited to the relevant components. Furthermore, the
   modular design allows for independent testing and development of different components, improving overall development
   efficiency.

2. **Connection Pooling**: Our system utilizes a connection pool to manage database connections, which enhances the
   application's performance and responsiveness.
   Connection pooling reduces the overhead of creating and closing connections by reusing existing ones, thereby
   speeding up database operations. Additionally, the pool can be configured to grow or shrink based on demand, ensuring
   optimal resource usage and scalability.

3. **Efficient Data Updates**: When updating data in the system, our application modifies only the affected row in the
   database, minimizing the impact on performance. This efficient approach to data updates reduces the time and
   resources required for database operations, ensuring that the application remains responsive even under heavy loads.

4. **User-friendly Interface**: The user interface of our application is designed to be intuitive and easy to navigate,
   providing a seamless experience for both managers and users. The clear organization of the interface components and
   the logical flow between different pages make it simple for users to access the information and functionality they
   need.

5. **Robust Security**: Our system implements a secure authentication and authorization mechanism, ensuring that only
   authorized users can access the appropriate features and data. The use of encrypted passwords and role-based access
   control helps to protect sensitive information and maintain the integrity of the application.

6. **Modular Codebase**: The modular structure of the codebase, with well-defined packages and classes, makes the
   application easier to understand and maintain. The clear separation of responsibilities within the code ensures that
   developers can quickly locate and modify the relevant sections when needed, improving overall development speed and
   reducing the likelihood of introducing errors.

In summary, our system offers numerous benefits, including ease of extension, improved performance and responsiveness,
efficient data updates, a user-friendly interface, robust security, and a modular codebase. These advantages make our
application an ideal choice for managing user accounts and stock information.

## GUI relationships

1. **HomePage**: The HomePage serves as the entry point of the application, where users can choose to log in as a
   customer or a manager, or sign up for a new account. From here, users can navigate to the CustomerLogin,
   ManagerLogin, or SignUp panels.

2. **CustomerLogin and ManagerLogin**: These panels are responsible for user authentication. Depending on the user type,
   they will enter their email and password to log in to the system. Upon successful login, customers will be directed
   to the UserMenuPage, while managers will be directed to the ManagerFirstPage.

3. **UserMenuPage**: This page serves as the main menu for customers, providing access to the following pages:
    - StockDisplayPage: Allows users to view and buy stocks.
    - ManageAccountPage: Users can manage their account settings, such as updating their password or personal
      information.
    - UserStockPage: Displays the user's current stock holdings and allows them to sell stocks.

4. **ManagerFirstPage**: This is the main menu for managers, providing access to the following pages:
    - AddEditDeleteStocks: Managers can add, edit, or delete stock information.
    - UserRequestsPage: Managers can view and approve or decline user registration requests.
    - UserNotifyPage: Managers can send notifications or messages to users.

5. **SignUp**: This panel allows new users to register for an account. After registration, their status will be set to "
   pending," and their request will appear in the UserRequestsPage for manager approval.

6. **WelcomePanel**: This panel is displayed after a successful login, welcoming the user to the system and providing a
   brief introduction to the application.

7. **addEditDeleteStocks**: This panel allows managers to add new stocks, edit existing stock information, and delete
   stocks from the system.

8. **UserNotifyPage**: Managers can use this panel to send notifications or messages to users, which will be stored in
   the Message table in the database.

9. **UserRequestsPage**: Managers can view pending user registration requests in this panel and decide whether to
   approve or decline them. Approved users will be able to access the system, while declined users will be denied
   access.

These GUI components are interconnected, providing smooth navigation between different pages and ensuring a
user-friendly experience. The relationships between these components are designed to reflect the logical flow of the
application, making it easy for users to access the features and information they need.