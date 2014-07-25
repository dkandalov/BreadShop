package net.digihippo.bread;

import static java.lang.Math.min;

public class WholesaleOrder {
    private int totalQuantity;

    public WholesaleOrder(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void tryToFill(int orderQuantity, int orderId, Account account) {
        if (totalQuantity > 0 && orderQuantity > 0) {
            int actualQuantity = min(totalQuantity, orderQuantity);
            totalQuantity -= actualQuantity;

            account.orderFilled(orderId, actualQuantity);
        }
    }
}
