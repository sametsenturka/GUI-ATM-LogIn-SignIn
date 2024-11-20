/*
*
* @author Samet SENTURK
* @date 11-14-2024
* @version 4.2
*
* */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static final ArrayList<Integer> acctNums = new ArrayList<>(Arrays.asList(12345, 67890));
    private static final ArrayList<String> acctPINs = new ArrayList<>(Arrays.asList("1234", "5678"));
    private static final ArrayList<String> acctNames = new ArrayList<>(Arrays.asList("Joseph", "Samet"));
    private static final ArrayList<String> acctSurnames = new ArrayList<>(Arrays.asList("LEDET", "SENTURK"));
    private static final ArrayList<Double> acctBalances = new ArrayList<>(Arrays.asList(1000.00, 1500.00));
    private static int currentUserIndex = -1;

    public static void main(String[] args) {
        WelcomeMenu();
    }
    
    public static void WelcomeMenu(){
        JFrame frame = new JFrame("Welcome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 170);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Login or Sign In", SwingConstants.CENTER);
        frame.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton signInButton = new JButton("Sign In");

        buttonPanel.add(loginButton);
        buttonPanel.add(signInButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginScreen();
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignInScreen();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showSignInScreen(){
        
        int accNo = Integer.parseInt(JOptionPane.showInputDialog("Add Account No: "));
        acctNums.add(accNo);
        
        String accPIN = JOptionPane.showInputDialog("Add Account PIN: ");
        acctPINs.add(accPIN);
        
        String accName = JOptionPane.showInputDialog("Add Account Name: ");
        acctNames.add(accName);

        String accSurname = JOptionPane.showInputDialog("Add Surname: ");
        acctSurnames.add(accSurname);
        
        double accBal = Double.parseDouble(JOptionPane.showInputDialog("Add Balance: "));
        acctBalances.add(2000.00);
    }

    public static void showLoginScreen() {
        JFrame frame = new JFrame("ATM Login");
        frame.setSize(300, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel acctNumLabel = new JLabel("     Account Number:");
        JTextField acctNumField = new JTextField();
        JLabel pinLabel = new JLabel("      PIN:");
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
                if (index != -1 && acctPINs.get(index).equals(pin)) {
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
        frame.setSize(300, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 1));

        JLabel welcomeLabel = new JLabel("Hello, " + acctNames.get(currentUserIndex) + " " + acctSurnames.get(currentUserIndex));
        JButton balanceButton = new JButton("View Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawalButton = new JButton("Withdrawal");
        JButton changeNameButton = new JButton("Change Name");

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Balance: $" + acctBalances.get(currentUserIndex));
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog("Enter amount to deposit:");
                double amount = Double.parseDouble(amountStr);
                if (validDeposit(amount)) {
                    acctBalances.set(currentUserIndex, amount);
                    JOptionPane.showMessageDialog(frame, "Deposit successful. New balance: $" + acctBalances.get(currentUserIndex));
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
                if (validWithdrawal(acctBalances.get(currentUserIndex), amount)) {
                    acctBalances.set(currentUserIndex, amount);
                    JOptionPane.showMessageDialog(frame, "Withdrawal successful. New balance: $" + acctBalances.get(currentUserIndex));
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
                acctNames.set(currentUserIndex, newName);
                acctSurnames.set(currentUserIndex, newSurname);
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

    public static int findAcct(ArrayList<Integer> acctNums, int acctNum) {
        for (int i = 0; i < acctNums.size(); i++) {
            if (acctNums.get(i) == acctNum) {
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
