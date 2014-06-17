package net.digihippo.bread;

import java.util.HashMap;
import java.util.Map;

public class BreadShop {
    public static int PRICE_OF_BREAD = 12;

    private final OutboundEvents events;
    private final AccountRepository accountRepository = new AccountRepository();

    public BreadShop(OutboundEvents events) {
        this.events = events;
    }

    public void createAccount(int id) {
        Account newAccount = new Account(id, events);
        accountRepository.addAccount(id, newAccount);
        events.accountCreatedSuccessfully(id);
    }

    public void deposit(int accountId, int creditAmount) {
        Account account = accountRepository.getAccount(accountId);
        if (account != null) {
            int newBalance = account.deposit(creditAmount);
            events.newAccountBalance(accountId, newBalance);
        } else {
            events.accountNotFound(accountId);
        }
    }

    public void placeOrder(int accountId, int orderId, int amount) {
        Account account = accountRepository.getAccount(accountId);
        if (account != null) {
            int cost = amount * PRICE_OF_BREAD;
            account.placeOrder(orderId, cost, amount);
        } else {
            events.accountNotFound(accountId);
        }
    }

    public void cancelOrder(int accountId, int orderId) {
        Account account = accountRepository.getAccount(accountId);
        if (account == null)
        {
            events.accountNotFound(accountId);
            return;
        }
        account.cancelOrder(orderId, PRICE_OF_BREAD);
    }

    public void placeWholesaleOrder() {
        throw new UnsupportedOperationException("Implement me in Objective A");
    }

    public void onWholesaleOrder(int quantity) {
        throw new UnsupportedOperationException("Implement me in Objective B");
    }

    private static class AccountRepository {
        private final Map<Integer, Account> accounts = new HashMap<Integer, Account>();

        void addAccount(int id, Account newAccount) {
            accounts.put(id, newAccount);
        }

        Account getAccount(int accountId) {
            return accounts.get(accountId);
        }
    }
}
