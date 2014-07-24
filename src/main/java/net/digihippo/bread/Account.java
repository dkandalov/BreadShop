package net.digihippo.bread;

import java.util.Map;
import java.util.TreeMap;

public class Account {
    private final int id;
    private final OutboundEvents events;
    private int balance = 0;
    private final Map<Integer, Integer> quantityByOrderId = new TreeMap<Integer, Integer>();

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
        quantityByOrderId.put(orderId, amount);
    }

    public void cancelOrder(int orderId, int price) {
        Integer cancelledQuantity = quantityByOrderId.remove(orderId);
        if (cancelledQuantity == null || cancelledQuantity == 0) {
            events.orderNotFound(id, orderId);
            return;
        }
        events.orderCancelled(id, orderId);

        deposit(cancelledQuantity * price);
    }

    public void accumulateOrdersInto(QuantityAccumulator accumulator) {
        for (Integer quantity : quantityByOrderId.values()) {
            accumulator.accumulate(quantity);
        }
    }

    public void fulfillOrders(OrderFiller orderFiller) {
        for (Map.Entry<Integer, Integer> entry : quantityByOrderId.entrySet()) {
            int orderId = entry.getKey();
            int quantity = entry.getValue();
            orderFiller.tryToFill(this, id, orderId, quantity, events);
        }
    }

    public void orderFilled(int orderId, int quantity) {
        int newQuantity = quantityByOrderId.get(orderId) - quantity;
        quantityByOrderId.put(orderId, newQuantity);
    }
}
