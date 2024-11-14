import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private static int[] acctNums = {12345, 67890};

  // ANY NAME.
    private static String[] acctPINs = {"1234", "5678"};
    private static String[] acctNames = {"Joseph", "Samet"};
    private static String[] acctSurnames = {"LEDET", "SENTURK"};
    private static double[] acctBalances = {1000.00, 1500.00};
    private static int currentUserIndex = -1;

    public static void main(String[] args) {
        showLoginScreen();
    }

    public static void showLoginScreen() {
        JFrame frame = new JFrame("ATM Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel acctNumLabel = new JLabel("Account Number:");
        JTextField acctNumField = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        frame.add(acctNumLabel);
        frame.add(acctNumField);
        frame.add(pinLabel);
        frame.add(pinField);
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int acctNum = Integer.parseInt(acctNumField.getText());
                String pin = new String(pinField.getPassword());

                int index = findAcct(acctNums, acctNum);
                if (index != -1 && acctPINs[index].equals(pin)) {
                    currentUserIndex = index;
                    frame.dispose();
                    showMenuScreen();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid account number or PIN.");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void showMenuScreen() {
        JFrame frame = new JFrame("ATM Menu");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 1));

        JLabel welcomeLabel = new JLabel("Hello, " + acctNames[currentUserIndex] + " " + acctSurnames[currentUserIndex]);
        JButton balanceButton = new JButton("View Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawalButton = new JButton("Withdrawal");
        JButton changeNameButton = new JButton("Change Name");

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Balance: $" + acctBalances[currentUserIndex]);
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog("Enter amount to deposit:");
                double amount = Double.parseDouble(amountStr);
                if (validDeposit(amount)) {
                    acctBalances[currentUserIndex] += amount;
                    JOptionPane.showMessageDialog(frame, "Deposit successful. New balance: $" + acctBalances[currentUserIndex]);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid deposit amount.");
                }
            }
        });

        withdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog("Enter amount to withdraw:");
                double amount = Double.parseDouble(amountStr);
                if (validWithdrawal(acctBalances[currentUserIndex], amount)) {
                    acctBalances[currentUserIndex] -= amount;
                    JOptionPane.showMessageDialog(frame, "Withdrawal successful. New balance: $" + acctBalances[currentUserIndex]);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid withdrawal amount or insufficient funds.");
                }
            }
        });

        changeNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog("Enter new name:");
                String newSurname = JOptionPane.showInputDialog("Enter new surname:");
                acctNames[currentUserIndex] = newName;
                acctSurnames[currentUserIndex] = newSurname;
                welcomeLabel.setText("Hello, " + newName + " " + newSurname);
                JOptionPane.showMessageDialog(frame, "Name changed successfully.");
            }
        });

        frame.add(welcomeLabel);
        frame.add(balanceButton);
        frame.add(depositButton);
        frame.add(withdrawalButton);
        frame.add(changeNameButton);

        frame.setVisible(true);
    }

    public static int findAcct(int[] acctNums, int acctNum) {
        for (int i = 0; i < acctNums.length; i++) {
            if (acctNums[i] == acctNum) {
                return i;
            }
        }
        return -1;
    }

    public static boolean validDeposit(double amount) {
        return amount > 0;
    }

    public static boolean validWithdrawal(double balance, double amount) {
        return (amount > 0) && (amount <= balance);
    }
}
