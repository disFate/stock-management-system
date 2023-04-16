package views;

/**
 * @Author: Tsuna
 * @Date: 2023-04-13-19:30
 * @Description:
 */
import javax.swing.*;
import java.awt.*;

public abstract class BaseView extends JFrame {

    protected static final int WINDOW_WIDTH = 600;
    protected static final int WINDOW_HEIGHT = 350;
    protected static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    protected static final int PADDING = 20;

    protected JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        return formPanel;
    }

    protected GridBagConstraints createGridBagConstraints(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }

    protected void setupWindow(String title) {
        setTitle(title);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }
}
