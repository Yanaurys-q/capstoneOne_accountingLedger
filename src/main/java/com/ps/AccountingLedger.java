package com.ps;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                    System.out.println("===============================================");
                    System.out.println("|                                             |");
                    System.out.println("|      Thank you for using My Accounting      |");
                    System.out.println("|                   Ledger                    |");
                    System.out.println("|                                             |");
                    System.out.println("|             Make Money Moves!               |");
                    System.out.println("|                 Goodbye :)                  |");
                    System.out.println("|                                             |");
                    System.out.println("|         $   $   $   $   $   $   $           |");
                    System.out.println("===============================================");
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
            System.out.println("4) Reports");
            System.out.println("5) Back to Main Menu");
            System.out.print("Choose an option (1-5): ");
            String givenCommand = scanner.nextLine();

            ArrayList<String[]> transactions = readTransactions();

            switch (givenCommand)
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
                reportsMenu(scanner, transactions);
                break;

                case "5":
                    inLedger = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void reportsMenu(Scanner scanner, ArrayList<String[]> transactions)
    {
        boolean inReports = true;
        while (inReports)
        {
            System.out.println("\nReports Menu:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Back to Ledger Menu");
            System.out.print("Choose an option (1-6): ");
            String givenCommand = scanner.nextLine();

            switch (givenCommand)
            {
                case "1":
                    ArrayList<String[]> mtd = filterByDate(transactions, "monthToDate");
                    displayTransactions(mtd, "all");
                    break;
                case "2":
                    ArrayList<String[]> prevMonth = filterByDate(transactions, "previousMonth");
                    displayTransactions(prevMonth, "all");
                    break;
                case "3":
                    ArrayList<String[]> ytd = filterByDate(transactions, "yearToDate");
                    displayTransactions(ytd, "all");
                    break;
                case "4":
                    ArrayList<String[]> prevYear = filterByDate(transactions, "previousYear");
                    displayTransactions(prevYear, "all");
                    break;
                case "5":
                    System.out.print("Enter vendor name to search: ");
                    String vendor = scanner.nextLine();
                    ArrayList<String[]> filtered = filterByVendor(transactions, vendor);
                    displayTransactions(filtered, "all");
                    break;
                case "6":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static ArrayList<String[]> filterByVendor(ArrayList<String[]> transactions, String vendor)
    {
        ArrayList<String[]> filtered = new ArrayList<>();
        for (String[] t : transactions)
        {
            if (t[3].equalsIgnoreCase(vendor))
            {
                filtered.add(t);
            }
        }
        return filtered;
    }

    private static ArrayList<String[]> filterByDate(ArrayList<String[]> transactions, String type)
    {
        ArrayList<String[]> filtered = new ArrayList<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (String[] t : transactions)
        {
            try
            {
                LocalDate date = LocalDate.parse(t[0], formatter);
                switch (type)
                {
                    case "monthToDate":
                        if (date.getYear() == now.getYear() && date.getMonth() == now.getMonth())
                            filtered.add(t);
                        break;
                    case "previousMonth":
                        LocalDate prevMonth = now.minusMonths(1);
                        if (date.getYear() == prevMonth.getYear() && date.getMonth() == prevMonth.getMonth())
                            filtered.add(t);
                        break;
                    case "yearToDate":
                        if (date.getYear() == now.getYear())
                            filtered.add(t);
                        break;
                    case "previousYear":
                        if (date.getYear() == now.getYear() - 1)
                            filtered.add(t);
                        break;
                }
            }
            catch (Exception ignored)
            {

            }
        }
        return filtered;
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
            boolean show = false;

            switch (type)
            {
                case "all":
                    show = true;
                    break;
                case "deposit":
                    if (amount > 0) show = true;
                    break;
                case "payment":
                    if (amount < 0) show = true;
                    break;
                default:
                    show = false;
            }

            if (show)
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
            FileWriter writer = new FileWriter("transactions.csv", true);
            writer.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount + "\n");
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }
}

