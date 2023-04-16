package views.component;

/**
 * @Author: Tsuna
 * @Date: 2023-04-13-20:58
 * @Description:
 */
import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 30;

    public CustomButton(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 14));
        setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    }
}

