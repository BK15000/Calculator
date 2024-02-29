import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * Brian Kennedy
 * CSE385 HW 7
 * 11-7-2023
 */
public class calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String currentInput = "";
    private double firstNumber;
    private double secondNumber;
    private char operator;
    private double result;

    public calculator() {
        setTitle("Calculator");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Create the display field for showing the input and results
        display = new JTextField(10);
        display.setEditable(false);
        GridBagConstraints displayConstraints = new GridBagConstraints();
        displayConstraints.gridwidth = 4;
        displayConstraints.fill = GridBagConstraints.BOTH;
        displayConstraints.weightx = 1.0;
        displayConstraints.insets = new Insets(5, 5, 5, 5);
        add(display, displayConstraints);

        // Define button labels for the calculator layout
        String[][] buttonLabels = {
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", "C", "=", "+"}
        };

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonConstraints.insets = new Insets(5, 5, 5, 5);

        // Create buttons based on labels
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JButton button = new JButton(buttonLabels[i][j]);
                button.addActionListener(this);
                buttonConstraints.gridx = j;
                buttonConstraints.gridy = i + 1;
                add(button, buttonConstraints);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.matches("\\d")) {
            // Handle numeric input
            currentInput += actionCommand;
            display.setText(currentInput);
        } else if ("+-*/".contains(actionCommand)) {
            // Handle operator input
            if (!currentInput.isEmpty()) {
                firstNumber = Double.parseDouble(currentInput);
                operator = actionCommand.charAt(0);
                currentInput = "";
            }
        } else if ("=".equals(actionCommand)) {
            // Handle equals button
            if (!currentInput.isEmpty()) {
                // Handle concurrent operations
                if (secondNumber != 0) {
                    firstNumber = secondNumber;
                    secondNumber = Double.parseDouble(currentInput);
                    result = performCalculation(firstNumber, secondNumber, operator);
                    display.setText(String.valueOf(result));
                    secondNumber = result;
                } else {
                    secondNumber = Double.parseDouble(currentInput);
                    result = performCalculation(firstNumber, secondNumber, operator);
                    display.setText(String.valueOf(result));
                    secondNumber = result;
                }
            }
        } else if ("C".equals(actionCommand)) {
            // Handle clear button
            currentInput = "";
            firstNumber = 0;
            secondNumber = 0;
            display.setText("");
        }
    }

    private double performCalculation(double num1, double num2, char op) {
        // Perform the specified arithmetic operation
        double result = 0;
        switch (op) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    result = Double.NaN;
                }
                break;
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            calculator calculator = new calculator();
            calculator.setVisible(true);
        });
    }
}
