package net.digihippo.bread;

import java.util.HashMap;
import java.util.Map;

public class BreadShop {
    public static int PRICE_OF_BREAD = 12;

    private final OutboundEvents events;
    private final Map<Integer, Account> accounts = new HashMap<Integer, Account>();

    public BreadShop(OutboundEvents events) {
        this.events = events;
    }

    public void createAccount(int id) {
        Account newAccount = new Account(id, events);
        addAccount(id, newAccount);
        events.accountCreatedSuccessfully(id);
    }

    public void deposit(int accountId, int creditAmount) {
        Account account = getAccount(accountId);
        if (account != null) {
            account.deposit(creditAmount);
        } else {
            events.accountNotFound(accountId);
        }
    }

    public void placeOrder(int accountId, int orderId, int amount) {
        Account account = getAccount(accountId);
        if (account != null) {
            int cost = amount * PRICE_OF_BREAD;
            account.placeOrder(orderId, cost, amount);
        } else {
            events.accountNotFound(accountId);
        }
    }

    public void cancelOrder(int accountId, int orderId) {
        Account account = getAccount(accountId);
        if (account == null)
        {
            events.accountNotFound(accountId);
            return;
        }
        account.cancelOrder(orderId, PRICE_OF_BREAD);
    }

    public void placeWholesaleOrder() {
        OrderAccumulator accumulator = new OrderAccumulator(events);
        for (Account account : accounts.values()) {
            account.accumulateOrdersInto(accumulator);
        }
        accumulator.finished();
    }

    public void onWholesaleOrder(int quantity) {
        throw new UnsupportedOperationException("Implement me in Objective B");
    }

    private void addAccount(int id, Account newAccount) {
        accounts.put(id, newAccount);
    }

    private Account getAccount(int accountId) {
        return accounts.get(accountId);
    }
}
