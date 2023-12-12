/*
ATM Interface

We have all come across ATMs in our cities and it is built on Java. This complex project consists of
five different classes and is a console-based application. When the system starts the user is
prompted with user id and user pin. On entering the details successfully, then ATM functionalities
are unlocked. The project allows to perform following operations:

1.Transactions History
2.Withdraw
3.Deposit
4.Transfer
5.Quit

*/

import java.util.Scanner;

class User {
    private String userId;
    private String pin;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }
}

class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Insufficient funds for transfer!");
        }
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class ATM {
    private User user;
    private BankAccount account;
    private Scanner scanner;
    private TransactionHistory transactionHistory;

    public ATM(User user, BankAccount account) {
        this.user = user;
        this.account = account;
        this.scanner = new Scanner(System.in);
        this.transactionHistory = new TransactionHistory();
    }

    public void start() {
        System.out.println("Welcome to the ATM!");
        authenticateUser();

        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayTransactionHistory();
                    break;
                case 2:
                    performWithdrawal();
                    break;
                case 3:
                    performDeposit();
                    break;
                case 4:
                    performTransfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 5);
    }

    private void authenticateUser() {
        System.out.print("Enter User ID: ");
        String enteredUserId = scanner.next();
        System.out.print("Enter PIN: ");
        String enteredPin = scanner.next();

        if (enteredUserId.equals(user.getUserId()) && enteredPin.equals(user.getPin())) {
            System.out.println("Authentication successful. Welcome, " + user.getUserId() + "!");
        } else {
            System.out.println("Authentication failed. Exiting...");
            System.exit(0);
        }
    }

    private void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    private void displayTransactionHistory() {
        transactionHistory.displayHistory();
    }

    private void performWithdrawal() {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
        transactionHistory.addTransaction(new Transaction("Withdrawal", amount));
        System.out.println("Withdrawal successful!");
    }

    private void performDeposit() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
        transactionHistory.addTransaction(new Transaction("Deposit", amount));
        System.out.println("Deposit successful!");
    }

    private void performTransfer() {
        System.out.print("Enter recipient's account number: ");
        String recipientAccountNumber = scanner.next();

        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();

        BankAccount recipient = new BankAccount(recipientAccountNumber);
        account.transfer(recipient, amount);
        transactionHistory.addTransaction(new Transaction("Transfer to " + recipientAccountNumber, amount));
    }
}

class TransactionHistory {
    private java.util.List<Transaction> transactions;

    public TransactionHistory() {
        this.transactions = new java.util.ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void displayHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        User user = new User("Mansi", "Mansi@1234");
        BankAccount account = new BankAccount("123456789");
        ATM atm = new ATM(user, account);
        atm.start();
    }
}
