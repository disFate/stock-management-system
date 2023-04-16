import views.*;

/**
 * @Author: Tsuna
 * @Date: 2023-04-13-19:34
 * @Description:
 */
public class StockManagementSystem {

    public static void main(String[] args) {
        // Create and display the LoginView
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        // Uncomment the following lines to test other views
        /*
        RegisterView registerView = new RegisterView();
        registerView.setVisible(true);

        PortfolioView portfolioView = new PortfolioView();
        portfolioView.setVisible(true);

        StockView stockView = new StockView();
        stockView.setVisible(true);

        TradeView tradeView = new TradeView();
        tradeView.setVisible(true);

        UserView userView = new UserView();
        userView.setVisible(true);
        */
    }
}
