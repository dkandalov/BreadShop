package net.digihippo.bread;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private final int id;
    private final OutboundEvents events;
    private int balance = 0;
    private final Map<Integer, Integer> orders = new HashMap<Integer, Integer>();

    public Account(int id, OutboundEvents events) {
        this.id = id;
        this.events = events;
    }

    public int deposit(int creditAmount) {
        balance += creditAmount;
        return balance;
    }

    public void placeOrder(int orderId, int cost, int amount) {
        if (balance >= cost) {
            addOrder(orderId, amount);
            int newBalance = deposit(-cost);
            events.orderPlaced(id, amount);
            events.newAccountBalance(id, newBalance);
        } else {
            events.orderRejected(id);
        }
    }

    private void addOrder(int orderId, int amount) {
        orders.put(orderId, amount);
    }

    public void cancelOrder(int orderId, int price) {
        Integer cancelledQuantity = orders.remove(orderId);
        if (cancelledQuantity == null) {
            events.orderNotFound(id, orderId);
            return;
        }

        int newBalance = deposit(cancelledQuantity * price);
        events.orderCancelled(id, orderId);
        events.newAccountBalance(id, newBalance);
    }
}
