/*
*
* @author Samet SENTURK
* @date 11-14-2024
* @version 3.0
*
* */

import java.util.Scanner;

public class Assignment3 {
    public static void main(String[] args) {

        //Verileri buraya yazacaz.
        int[] acctNums = {12345, 67890};
        String[] acctPINs = {"1234", "5678"};

        String[] acctNames = {"Joseph", "Samet"};
        String[] acctSurnames = {"LEDET", "SENTURK"};

        double[] acctBalances = {1000.00, 1500.00};

        bankLogin(acctNums, acctNames, acctSurnames, acctPINs, acctBalances);
    }

    public static int menuDisplay(String[] options, Scanner scanner) {
        System.out.println("What would you like to do today?");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }
        System.out.println("0 to Quit");
        System.out.print("Please enter your selection >> ");
        return scanner.nextInt();
    }

    public static void atm(String[] names, String[] surnames, double[] balances, int index, Scanner scanner) {
        String[] options = {"Account Balance", "Deposit", "Withdrawal", "Change Name"};
        int choice;

        do {
            System.out.println("Hello " + names[index] + " " + surnames[index]);
            choice = menuDisplay(options, scanner);
            switch (choice) {
                case 1:
                    System.out.println("Balance: " + balances[index]);
                    break;
                case 2:
                    // Handle withdrawal
                    break;
                case 3:
                    // Handle deposit
                    break;
                case 4:
                    System.out.println("Enter new name:");
                    names[index] = scanner.next();
                    System.out.println("Enter new surname:");
                    surnames[index] = scanner.next();
                    break;
                case 0:
                    System.out.println("Thank you for using our ATM. Have a nice day!");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
        while (choice != 0);
    }

    //Account Numarasi ile Girilen Account numarasini karsilastir.
    public static int findAcct(int[] acctNums, int acctNum) {
        for (int i = 0; i < acctNums.length; i++) {
            if (acctNums[i] == acctNum) {
                return i;
            }
        }
        return -1;
    }
    public static void bankLogin(int[] acctNums, String[] acctNames, String[] acctSurnames, String[] acctPINs, double[] acctBalances) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number:");
        int acctNum = scanner.nextInt();
        int index = findAcct(acctNums, acctNum);

        if (index == -1) {
            System.out.println("ERROR: Account not found");
            return;
        }

        System.out.print("Enter PIN:");
        String pin = scanner.next();

        // IgnoreCase Yapma!
        if (!acctPINs[index].equals(pin)) {
            System.out.println("ERROR: Incorrect PIN");
            return;
        }

        //Login olduktan sonra bu calisacak.
        atm(acctNames, acctSurnames, acctBalances, index, scanner);
    }

    public static boolean validDeposit(double amount) {
        return amount > 0;
    }

    public static boolean validWithdrawal(double balance, double amount) {
        return (amount > 0) && (amount <= balance);
    }

    public static String cashGiven(double amount) {
        double[] paralar = {200, 100, 50, 20, 10, 5, 1, 0.50, 0.25, 0.10, 0.05, 0.01};
        StringBuilder result = new StringBuilder();

        for (int i=0; i<paralar.length; i++) {
            double para = paralar[i];
            int count = (int) (amount / para);
            amount -= count * para;
            result.append(count).append(" - ").append(para).append("\n");
        }

        return result.toString();
    }
}
