import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class MyFinalsLabExam implements ActionListener {

    JFrame frame;
    JTextField textfield;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[12];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton, negButton;
    JButton percentButton, expButton, sqrtButton;
    JPanel panel;

    StringBuilder expression = new StringBuilder();
    private boolean isResultDisplayed = false;

    public MyFinalsLabExam() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 600);
        frame.setLayout(null);

        textfield = new JTextField();
        textfield.setBounds(50, 25, 300, 50);
        textfield.setHorizontalAlignment(JTextField.RIGHT);
        textfield.setEditable(false);
        textfield.setFont(new Font("Arial", Font.PLAIN, 20));

        // Function Buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("x");
        divButton = new JButton("÷");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("Clr");
        negButton = new JButton("(-)");
        percentButton = new JButton("%");
        expButton = new JButton("^");
        sqrtButton = new JButton("√");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = negButton;
        functionButtons[9] = percentButton;
        functionButtons[10] = expButton;
        functionButtons[11] = sqrtButton;

        Font buttonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 22);

        for (int i = 0; i < 12; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(buttonFont);
        }

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(buttonFont);
        }

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 410);
        panel.setLayout(new GridLayout(7, 4, 10, 10));

        // Row 1
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        // Row 2
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        // Row 3
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        // Row 4
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);
        // Row 5
        panel.add(percentButton);
        panel.add(expButton);
        panel.add(sqrtButton);
        panel.add(negButton);
        // Row 6
        panel.add(delButton);
        panel.add(clrButton);
        panel.add(new JLabel());
        panel.add(new JLabel());
        // Row 7 (all blanks for guaranteed spacing)
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());

        frame.add(panel);
        frame.add(textfield);

        frame.setVisible(true);
    }

    private void appendToTextField(String s) {
        if (isResultDisplayed) {
            textfield.setText("");
            expression.setLength(0);
            isResultDisplayed = false;
        }
        expression.append(s);
        textfield.setText(expression.toString());
    }

    private void clearAll() {
        expression.setLength(0);
        textfield.setText("");
        isResultDisplayed = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                appendToTextField(String.valueOf(i));
                return;
            }
        }

        for (int i = 0; i < 12; i++) {
            if (e.getSource() == functionButtons[i]) {
                String text = functionButtons[i].getText();
                switch (text) {
                    case "+":
                    case "-":
                    case "x":
                    case "÷":
                    case "%":
                    case "^":
                        if (expression.length() > 0 && !endsWithOperator(expression.toString())) {
                            if (isResultDisplayed) isResultDisplayed = false;
                            appendToTextField(text);
                        }
                        break;
                    case ".":
                        if (expression.length() == 0 || endsWithOperator(expression.toString())) {
                            appendToTextField("0.");
                        } else if (!getLastNumber(expression.toString()).contains(".")) {
                            appendToTextField(".");
                        }
                        break;
                    case "=":
                        evaluateExpression();
                        break;
                    case "Del":
                        if (expression.length() > 0) {
                            expression.deleteCharAt(expression.length() - 1);
                            textfield.setText(expression.toString());
                        }
                        break;
                    case "Clr":
                        clearAll();
                        break;
                    case "(-)":
                        negateLastNumber();
                        break;
                    case "√":
                        sqrtLastNumber();
                        break;
                }
                return;
            }
        }
    }

    private boolean endsWithOperator(String expr) {
        if (expr.isEmpty()) return false;
        char c = expr.charAt(expr.length() - 1);
        switch (c) {
            case '+':
            case '-':
            case 'x':
            case '÷':
            case '%':
            case '^':
                return true;
            default:
                return false;
        }
    }

    private String getLastNumber(String expr) {
        if (expr.isEmpty()) return "";
        int i = expr.length() - 1;
        while (i >= 0 && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.' || (i > 0 && expr.charAt(i-1) == '-' && (i == 1 || !Character.isDigit(expr.charAt(i-2)))))) {
            i--;
        }
        return expr.substring(i + 1);
    }

    private void negateLastNumber() {
        String expr = expression.toString();
        String lastNumber = getLastNumber(expr);
        if (!lastNumber.isEmpty()) {
            int idx = expr.lastIndexOf(lastNumber);
            try {
                double number = Double.parseDouble(lastNumber);
                number = -number;
                expression.replace(idx, idx + lastNumber.length(), String.valueOf(number));
                textfield.setText(expression.toString());
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    private void sqrtLastNumber() {
        String expr = expression.toString();
        String lastNumber = getLastNumber(expr);
        if (!lastNumber.isEmpty()) {
            int idx = expr.lastIndexOf(lastNumber);
            try {
                double number = Double.parseDouble(lastNumber);
                if (number < 0) {
                    textfield.setText("Invalid");
                    isResultDisplayed = true;
                    return;
                }
                number = Math.sqrt(number);
                expression.replace(idx, idx + lastNumber.length(), String.valueOf(number));
                textfield.setText(expression.toString());
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    private void evaluateExpression() {
        try {
            String expr = expression.toString();
            if (expr.isEmpty() || endsWithOperator(expr)) return;
            double result = evaluate(expr);
            String resStr = (result == (long) result) ? String.valueOf((long) result) : String.valueOf(result);
            textfield.setText(resStr);
            expression.setLength(0);
            expression.append(resStr);
            isResultDisplayed = true;
        } catch (Exception ex) {
            textfield.setText("Error");
            expression.setLength(0);
            isResultDisplayed = true;
        }
    }

    private double evaluate(String expr) throws Exception {
        String postfix = infixToPostfix(expr);
        return evalPostfix(postfix);
    }

    private int precedence(char operator) {
        switch (operator) {
            case '^': return 3;
            case 'x':
            case '÷':
            case '%': return 2;
            case '+':
            case '-': return 1;
            default: return 0;
        }
    }

    private boolean isOperator(char c) {
        switch (c) {
            case '+':
            case '-':
            case 'x':
            case '÷':
            case '%':
            case '^':
                return true;
            default:
                return false;
        }
    }

    private String infixToPostfix(String expr) throws Exception {
        StringBuilder output = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    num.append(expr.charAt(i));
                    i++;
                }
                output.append(num).append(' ');
                continue;
            } else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(c)) {
                    output.append(operatorStack.pop()).append(' ');
                }
                operatorStack.push(c);
            }
            i++;
        }
        while (!operatorStack.isEmpty()) {
            output.append(operatorStack.pop()).append(' ');
        }
        return output.toString();
    }

    private double evalPostfix(String postfix) throws Exception {
        Stack<Double> numberStack = new Stack<>();
        String[] tokens = postfix.trim().split("\\s+");
        for (String token : tokens) {
            if (token.length() == 1 && isOperator(token.charAt(0))) {
                double rightOperand = numberStack.pop();
                double leftOperand = numberStack.pop();
                double result;
                switch (token.charAt(0)) {
                    case '+': result = leftOperand + rightOperand; break;
                    case '-': result = leftOperand - rightOperand; break;
                    case 'x': result = leftOperand * rightOperand; break;
                    case '÷':
                        if (rightOperand == 0) throw new Exception("Div by zero");
                        result = leftOperand / rightOperand; break;
                    case '%':
                        if (rightOperand == 0) throw new Exception("Mod by zero");
                        result = leftOperand % rightOperand; break;
                    case '^': result = Math.pow(leftOperand, rightOperand); break;
                    default: throw new Exception("Unknown op");
                }
                numberStack.push(result);
            } else {
                numberStack.push(Double.parseDouble(token));
            }
        }
        if (numberStack.size() != 1) throw new Exception("Invalid expr");
        return numberStack.pop();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyFinalsLabExam::new);
    }
}
