package net.digihippo.bread;

import java.util.Map;
import java.util.TreeMap;

public class Account {
    private final int id;
    private final OutboundEvents events;
    private int balance = 0;
    private final Map<Integer, Order> ordersById = new TreeMap<Integer, Order>();

    public Account(int id, OutboundEvents events) {
        this.id = id;
        this.events = events;
    }

    public int deposit(int creditAmount) {
        balance += creditAmount;
        events.newAccountBalance(id, balance);
        return balance;
    }

    public void placeOrder(int orderId, int cost, int amount) {
        if (balance >= cost) {
            addOrder(orderId, amount);
            deposit(-cost);
            events.orderPlaced(id, amount);
        } else {
            events.orderRejected(id);
        }
    }

    private void addOrder(int orderId, int amount) {
        ordersById.put(orderId, new Order(orderId, amount));
    }

    public void cancelOrder(int orderId, int price) {
        Order cancelledOrder = ordersById.remove(orderId);
        if (cancelledOrder == null || cancelledOrder.quantity == 0) {
            events.orderNotFound(id, orderId);
            return;
        }
        events.orderCancelled(id, orderId);

        deposit(cancelledOrder.quantity * price);
    }

    public void accumulateOrdersInto(QuantityAccumulator accumulator) {
        for (Order order : ordersById.values()) {
            accumulator.accumulate(order.quantity);
        }
    }

    public void fulfillOrders(WholesaleOrder wholesaleOrder) {
        for (Order order : ordersById.values()) {
            wholesaleOrder.tryToFill(order.quantity, order.id, this);
        }
    }

    public void orderFilled(int orderId, int quantity) {
        int newQuantity = ordersById.get(orderId).quantity - quantity;
        ordersById.put(orderId, new Order(orderId, newQuantity));

        events.orderFilled(id, orderId, quantity);
    }

    private static class Order {
        private final int id;
        private final int quantity;

        public Order(int id, int quantity) {
            this.id = id;
            this.quantity = quantity;
        }
    }
}
