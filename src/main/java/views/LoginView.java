package views;
import views.component.CustomButton;
import views.component.CustomInputField;

import javax.swing.*;
import java.awt.*;

public class LoginView extends BaseView {
    private CustomInputField emailField;
    private JPasswordField passwordField;
    private CustomButton loginButton;

    public LoginView() {
        setupWindow("Login");

        JPanel formPanel = createFormPanel();

        GridBagConstraints gbc;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(LABEL_FONT);
        gbc = createGridBagConstraints(0, 0);
        formPanel.add(emailLabel, gbc);

        emailField = new CustomInputField();
        gbc = createGridBagConstraints(1, 0);
        formPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(LABEL_FONT);
        gbc = createGridBagConstraints(0, 1);
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(emailField.getPreferredSize());
        gbc = createGridBagConstraints(1, 1);
        formPanel.add(passwordField, gbc);

        loginButton = new CustomButton("Login");
        gbc = createGridBagConstraints(1, 2);
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    // Add other methods and event listeners as needed
}
