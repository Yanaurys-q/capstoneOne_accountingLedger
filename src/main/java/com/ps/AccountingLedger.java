package com.ps;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class AccountingLedger
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running)
        {
            System.out.println("Welcome to the Accounting Ledger!");
            System.out.println("1) Add Deposit");
            System.out.println("2) Make Payment (Debit)");
            System.out.println("3) Ledger");
            System.out.println("4) Exit");
            System.out.print("Choose an option (1-4): ");
            String givenCommand = scanner.nextLine();

            switch (givenCommand)
            {
                case "1":
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter time (HH:MM:SS): ");
                    String time = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter vendor: ");
                    String vendor = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    String amount = scanner.nextLine();

                    saveTransaction(date, time, description, vendor, amount);
                    System.out.println("Deposit added!\n");
                    break;

                case "2":
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    date = scanner.nextLine();
                    System.out.print("Enter time (HH:MM:SS): ");
                    time = scanner.nextLine();
                    System.out.print("Enter description: ");
                    description = scanner.nextLine();
                    System.out.print("Enter vendor: ");
                    vendor = scanner.nextLine();
                    System.out.print("Enter amount (use negative for payment): ");
                    amount = scanner.nextLine();

                    saveTransaction(date, time, description, vendor, amount);
                    System.out.println("Payment added!\n");
                    break;

                case "3":
                    ledgerMenu(scanner);
                    break;

                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.\n");
                    break;
            }
        }

        scanner.close();
    }

    private static void ledgerMenu(Scanner scanner)
    {

        boolean inLedger = true;
        while (inLedger)
        {
            System.out.println("\nLedger Menu:");
            System.out.println("1) View All Transactions");
            System.out.println("2) View Deposits Only");
            System.out.println("3) View Payments Only");
            System.out.println("4) Back to Main Menu");
            System.out.print("Choose an option (1-4): ");
            String givenComnmand = scanner.nextLine();

            ArrayList<String[]> transactions = readTransactions();

            switch (givenComnmand)
            {
                case "1":
                    displayTransactions(transactions, "all");
                    break;
                case "2":
                    displayTransactions(transactions, "deposit");
                    break;
                case "3":
                    displayTransactions(transactions, "payment");
                    break;
                case "4":
                    inLedger = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static ArrayList<String[]> readTransactions()
    {
        ArrayList<String[]> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv")))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                if (parts.length == 5)
                {
                    transactions.add(parts);
                }
            }
        }
        catch (IOException ex)
        {
            System.out.println("Error reading transactions.csv: " + ex.getMessage());
        }
        return transactions;
    }

    private static void displayTransactions(ArrayList<String[]> transactions, String type)
    {
        System.out.println("\nDate       | Time     | Description         | Vendor         | Amount");
        System.out.println("-----------------------------------------------------------------------");
        for (int i = transactions.size() - 1; i >= 0; i--)
        {
            String[] t = transactions.get(i);
            double amount = Double.parseDouble(t[4]);
            if (type.equals("all") ||
                    (type.equals("deposit") && amount > 0) ||
                    (type.equals("payment") && amount < 0))
            {
                System.out.printf("%-10s | %-8s | %-18s | %-14s | %8.2f\n",
                        t[0], t[1], t[2], t[3], amount);
            }
        }
    }

    public static void saveTransaction(String date, String time, String description, String vendor, String amount)
    {
        try
        {
            FileWriter writer = new FileWriter("transactions.csv", true); // Append mode
            writer.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount + "\n");
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }
}

