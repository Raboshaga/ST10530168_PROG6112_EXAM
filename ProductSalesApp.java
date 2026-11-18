/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.productsalesapp;

/**
 *
 * @author Student
 */
//Question2
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// INTERFACE
interface IProductCalculations {

    double GetVat();

    double GetCommission();

    double GetTotal();
}


// PRODUCT CALCULATIONS CLASS
class ProductCalculations implements IProductCalculations {

    private double price;

    public ProductCalculations(double price) {

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        this.price = price;
    }

    @Override
    public double GetVat() {

        return price * 0.15;
    }

    @Override
    public double GetCommission() {

        return price * 0.05;
    }

    @Override
    public double GetTotal() {

        return price + GetVat() + GetCommission();
    }
}


// MAIN APPLICATION CLASS
public class ProductSalesApp extends JFrame {

    private JTextField customerName;

    private JComboBox<String> productCombo;

    private JTextField priceField;


    // CONSTRUCTOR
    public ProductSalesApp() {

        setTitle("Product App");

        setSize(450, 280);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        customerName = new JTextField(18);

        productCombo = new JComboBox<>(new String[]{
            "Coy Nights Monarch",
            "Restonic Barbados",
            "Sealy Phuket"
        });

        priceField = new JTextField(18);

        createMenu();

        createForm();
    }


    // MENU BAR
    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();

        // FILE MENU
        JMenu fileMenu = new JMenu("File");

        JMenuItem exit = new JMenuItem("Exit");

        exit.addActionListener(e -> System.exit(0));

        fileMenu.add(exit);


        // TOOLS MENU
        JMenu toolsMenu = new JMenu("Tools");

        JMenuItem process = new JMenuItem("Process");

        JMenuItem clear = new JMenuItem("Clear");

        process.addActionListener(e -> processSale());

        clear.addActionListener(e -> clearForm());

        toolsMenu.add(process);

        toolsMenu.add(clear);


        menuBar.add(fileMenu);

        menuBar.add(toolsMenu);

        setJMenuBar(menuBar);
    }


    // CREATE FORM
    private void createForm() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        panel.add(new JLabel("Customer Name:"));

        panel.add(customerName);

        panel.add(new JLabel("Select Product:"));

        panel.add(productCombo);

        panel.add(new JLabel("Enter Price:"));

        panel.add(priceField);


        JButton processButton = new JButton("PROCESS");

        processButton.addActionListener(e -> processSale());

        panel.add(new JLabel(""));

        panel.add(processButton);

        add(panel);
    }


    // PROCESS SALE
    private void processSale() {

        String name = customerName.getText().trim();

        String product =
            (String) productCombo.getSelectedItem();

        String priceText = priceField.getText().trim();


        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(this,"Please enter customer name.");
            return;
        }


        if (priceText.isEmpty()) {

            JOptionPane.showMessageDialog(this,"Please enter product price.");
            return;
        }


        try {

            double price = Double.parseDouble(priceText);

            if (price < 0) {

                JOptionPane.showMessageDialog(this,"Price cannot be negative.");
                return;
            }

            ProductCalculations calculation =
                new ProductCalculations(price);

            double vat = calculation.GetVat();

            double commission = calculation.GetCommission();

            double total = calculation.GetTotal();

            String message =
                "Customer Name: " + name +
                "\nProduct: " + product +
                "\nVAT: R " + String.format("%.2f", vat) +
                "\nCOMMISSION: R " + String.format("%.2f", commission) +
                "\nTOTAL: R " + String.format("%.2f", total);

            saveToFile(
                name,
                product,
                price,
                vat,
                commission,
                total
            );


            JOptionPane.showMessageDialog(this,message,"Message",JOptionPane.INFORMATION_MESSAGE);
        }

        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,"Please enter a valid price.");
        }

        catch (IOException e) {

            JOptionPane.showMessageDialog(this,"Error saving data to file.");
        }
    }


    // SAVE INFORMATION TO TEXT FILE
    private void saveToFile(
        String name,
        String product,
        double price,
        double vat,
        double commission,
        double total
    ) throws IOException {

        try (
            BufferedWriter writer =
                new BufferedWriter(
                    new FileWriter("data.txt", true)
                )
        ) {

            writer.write("DATA LOG");

            writer.newLine();

            writer.write("Customer Name: " + name);

            writer.newLine();

            writer.write("Product: " + product);

            writer.newLine();

            writer.write("VAT: R " + String.format("%.2f", vat));
            
            writer.newLine();

            writer.write("PRICE: R " + String.format("%.2f", price));
                
            writer.newLine();

            writer.write("COMMISSION: R " +String.format("%.2f", commission));

            writer.newLine();

            writer.write("TOTAL: R " + String.format("%.2f", total));

            writer.newLine();

            writer.newLine();
        }
    }


    // CLEAR FORM
    private void clearForm() {

        customerName.setText("");

        productCombo.setSelectedIndex(0);

        priceField.setText("");
    }


    // MAIN METHOD
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ProductSalesApp app = new ProductSalesApp();

            app.setVisible(true);

        });
    }
}