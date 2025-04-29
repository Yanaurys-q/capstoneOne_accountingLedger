package com.ps;
import java.io.FileWriter;
import java.io.IOException;
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
            System.out.println("3) Exit");
            System.out.print("Choose an option (1-3): ");
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