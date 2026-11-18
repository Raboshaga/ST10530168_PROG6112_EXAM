/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.question1main;

/**
 *
 * @author Student
 */
import java.util.Scanner;

// INTERFACE
interface IProducts {

    int GetTotalProductsSold();

    double GetAverageProductSold();

    String GetMostPopularProduct();
}


// PRODUCT CLASS
class Product implements IProducts {

    private String[] productNames = {
        "Coy Nights Monarch",
        "Restonic Barbados",
        "Sealy Phuket"
    };

    private int[][] monthlySales = new int[3][3];

    private int[] totalSales = new int[3];

    public Product(int[][] sales) {

        if (sales == null || sales.length != 3) {
            throw new IllegalArgumentException("Invalid sales data.");
        }

        for (int i = 0; i < 3; i++) {

            if (sales[i] == null || sales[i].length != 3) {
                throw new IllegalArgumentException("Invalid monthly sales.");
            }

            for (int j = 0; j < 3; j++) {

                if (sales[i][j] < 0) {
                    throw new IllegalArgumentException("Sales cannot be negative.");
                }

                monthlySales[i][j] = sales[i][j];

                totalSales[i] += sales[i][j];
            }
        }
    }

    @Override
    public int GetTotalProductsSold() {

        int total = 0;

        for (int i = 0; i < 3; i++) {
            total += totalSales[i];
        }

        return total;
    }

    @Override
    public double GetAverageProductSold() {

        return GetTotalProductsSold() / 9.0;
    }

    @Override
    public String GetMostPopularProduct() {

        int highest = 0;

        for (int i = 1; i < 3; i++) {

            if (totalSales[i] > totalSales[highest]) {
                highest = i;
            }
        }

        return productNames[highest];
    }

    public int[] getProductTotals() {
        return totalSales.clone();
    }

    public String[] getProductNames() {
        return productNames.clone();
    }
}


// PRINT PRODUCT CLASS
class PrintProduct {

    private Product product;

    public PrintProduct(Product product) {
        this.product = product;
    }

    public void PrintReport() {

        System.out.println("\nPRODUCT SALES REPORT - 2026");

        String[] names = product.getProductNames();

        int[] totals = product.getProductTotals();

        for (int i = 0; i < names.length; i++) {

            System.out.println(names[i] + ": " + totals[i]);
        }

        System.out.println("TOTAL PRODUCT SALES: "+ product.GetTotalProductsSold());

        System.out.printf("AVERAGE PRODUCT SALES: %.0f%n",product.GetAverageProductSold());
        
        System.out.println("MOST POPULAR PRODUCT: "+ product.GetMostPopularProduct());
    }
}


// MAIN CLASS
public class Question1Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String[] products = {
            "Coy Nights Monarch",
            "Restonic Barbados",
            "Sealy Phuket"
        };

        int[][] sales = new int[3][3];

        System.out.println("MATTRESS SALES REPORT");

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                System.out.print("Enter number of sales for month "+ (j + 1) + " for " + products[i] + " mattress: ");
                    
                while (!input.hasNextInt()) {

                    System.out.println("Please enter a valid whole number.");

                    input.next();
                }

                int value = input.nextInt();

                while (value < 0) {

                    System.out.println("Sales cannot be negative. Enter again:");
                        
                    while (!input.hasNextInt()) {
                        System.out.println("Enter a valid whole number.");
                        input.next();
                    }

                    value = input.nextInt();
                }

                sales[i][j] = value;
            }
        }

        Product product = new Product(sales);

        PrintProduct report = new PrintProduct(product);

        report.PrintReport();

        input.close();
    }
}