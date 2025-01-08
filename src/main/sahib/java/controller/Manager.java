package main.sahib.java.controller;

import main.sahib.java.model.*;
import main.sahib.java.view.MainFrame;

import javax.swing.*;
import java.io.*;
import java.util.Map;

public class Manager {
    private final QueueOfCustomers queueOfCustomers;
    private final ParcelMap parcelMap;
    private final Worker worker;
    private MainFrame mainFrame;

    // Constructor
    public Manager() {
        queueOfCustomers = new QueueOfCustomers();
        parcelMap = new ParcelMap();
        worker = new Worker();
    }

    public void loadData() throws IOException {
        // Load customer data
        BufferedReader customerReader = new BufferedReader(new FileReader("src/main/sahib/java/customers.txt"));
        String line;

        while ((line = customerReader.readLine()) != null) {
            line = line.trim(); // Remove leading/trailing spaces
            if (line.isEmpty()) continue; // Skip empty lines

            // Split the line using multiple delimiters: comma, space, or tab
            String[] data = line.split("[,\\s]+"); // Accepts commas, spaces, or tabs as delimiters

            if (data.length < 2) { // Ensure at least 2 elements for customers
                Log.getInstance().addEntry("Invalid customer data: " + line);
                continue; // Skip invalid lines
            }

            // Add valid customer
            queueOfCustomers.addCustomer(new Customer(data[0].trim(), data[1].trim()));
        }
        customerReader.close();

        // Load parcel data
        BufferedReader parcelReader = new BufferedReader(new FileReader("src/main/sahib/java/parcels.txt"));
        while ((line = parcelReader.readLine()) != null) {
            line = line.trim(); // Remove spaces
            if (line.isEmpty()) continue; // Skip empty lines

            // Split the line using multiple delimiters
            String[] data = line.split("[,\\s]+"); // Accepts commas, spaces, or tabs as delimiters

            if (data.length < 3) { // Ensure at least 3 elements for parcels
                Log.getInstance().addEntry("Invalid parcel data: " + line);
                continue; // Skip invalid lines
            }

            try {
                // Parse weight and add valid parcel
                double weight = Double.parseDouble(data[1].trim());
                boolean processed = Boolean.parseBoolean(data[2].trim());
                parcelMap.addParcel(new Parcel(data[0].trim(), weight, processed));
            } catch (NumberFormatException e) {
                Log.getInstance().addEntry("Invalid parcel weight: " + line);
            }
        }
        parcelReader.close();

        Log.getInstance().addEntry("Data loaded successfully.");
    }


    // Add a new customer dynamically
    public void addNewCustomer(String customerId, String name) {
        queueOfCustomers.addCustomer(new Customer(customerId, name));
        Log.getInstance().addEntry("New Customer Added: " + customerId + " - " + name);

        // Refresh GUI
        if (mainFrame != null) {
            mainFrame.refreshView(queueOfCustomers, parcelMap);
        }
    }

    // Process the next parcel for the next customer
    public void processNextParcel() {
        if (queueOfCustomers.getQueue().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No customers in the queue!");
            return;
        }

        Customer customer = queueOfCustomers.removeCustomer(); // Get the next customer
        Parcel nextParcel = null;

        // Find the first unprocessed parcel
        for (Map.Entry<String, Parcel> entry : parcelMap.getParcels().entrySet()) {
            if (!entry.getValue().isProcessed()) {
                nextParcel = entry.getValue();
                break;
            }
        }

        if (nextParcel == null) {
            JOptionPane.showMessageDialog(null, "No more parcels to process!");
            return;
        }

        // Process the parcel
        worker.processParcel(nextParcel);
        Log.getInstance().addEntry("Parcel " + nextParcel.getParcelId() +
                " processed for Customer " + customer.getCustomerId());

        // Refresh GUI
        if (mainFrame != null) {
            mainFrame.refreshView(queueOfCustomers, parcelMap);
        }
    }

    public void startGUI() {
        SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame(this, queueOfCustomers, parcelMap); // Pass 'this' to allow method calls
        });
    }

    public static void main(String[] args) {
        try {
            Manager manager = new Manager();
            manager.loadData();
            manager.startGUI();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
}
