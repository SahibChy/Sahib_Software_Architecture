package main.sahib.java.model;

import java.util.LinkedList;
import java.util.Queue;

public class QueueOfCustomers {
    private Queue<Customer> customerQueue;

    public QueueOfCustomers() {
        customerQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer) {
        customerQueue.add(customer);
    }

    public Customer removeCustomer() {
        return customerQueue.poll();
    }

    public boolean isEmpty() {
        return customerQueue.isEmpty();
    }

    public Queue<Customer> getQueue() {
        return customerQueue;
    }
}