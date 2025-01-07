package main.sahib.java.view;

import main.sahib.java.controller.Manager;
import main.sahib.java.model.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTextArea customerArea, parcelArea, logArea;
    private Manager manager;

    // Constructor
    public MainFrame(Manager manager, QueueOfCustomers queue, ParcelMap parcelMap) {
        this.manager = manager;

        setTitle("Parcel Processing System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panels for display
        JPanel displayPanel = new JPanel(new GridLayout(1, 3));
        customerArea = new JTextArea();
        parcelArea = new JTextArea();
        logArea = new JTextArea();

        displayPanel.add(new JScrollPane(customerArea));
        displayPanel.add(new JScrollPane(parcelArea));
        displayPanel.add(new JScrollPane(logArea));
        add(displayPanel, BorderLayout.CENTER);

        // Control buttons
        JPanel controlPanel = new JPanel();
        JButton addCustomerButton = new JButton("Add Customer");
        JButton processNextButton = new JButton("Process Next Parcel");

        controlPanel.add(addCustomerButton);
        controlPanel.add(processNextButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Button Actions
        addCustomerButton.addActionListener(e -> addNewCustomer());
        processNextButton.addActionListener(e -> manager.processNextParcel());

        refreshView(queue, parcelMap);
        setVisible(true);
    }

    // Refresh data on GUI
    public void refreshView(QueueOfCustomers queue, ParcelMap parcelMap) {
        customerArea.setText("Customers in Queue:\n");
        for (Customer c : queue.getQueue()) {
            customerArea.append(c.getCustomerId() + " - " + c.getName() + "\n");
        }

        parcelArea.setText("Parcels:\n");
        for (Parcel p : parcelMap.getParcels().values()) {
            parcelArea.append(p.getParcelId() + " - " + (p.isProcessed() ? "Processed" : "Pending") + "\n");
        }

        logArea.setText(Log.getInstance().getLogs());
    }

    // Add a new customer dynamically
    private void addNewCustomer() {
        String customerId = JOptionPane.showInputDialog("Enter Customer ID:");
        String name = JOptionPane.showInputDialog("Enter Customer Name:");

        if (customerId != null && name != null && !customerId.isEmpty() && !name.isEmpty()) {
            manager.addNewCustomer(customerId, name);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Customer not added.");
        }
    }
}
