package views.component;

/**
 * @Author: Tsuna
 * @Date: 2023-04-13-20:58
 * @Description:
 */
import javax.swing.*;
import java.awt.*;

public class CustomInputField extends JTextField {
    private static final int INPUT_WIDTH = 100;
    private static final int INPUT_HEIGHT = 25;

    public CustomInputField() {
        setFont(new Font("Arial", Font.PLAIN, 14));
        setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
    }
}
