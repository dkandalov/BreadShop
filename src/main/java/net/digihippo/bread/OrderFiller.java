package net.digihippo.bread;

public class OrderFiller {
    private int totalQuantity;

    public OrderFiller(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void tryToFill(Account account, int accountId, int orderId, int orderQuantity, OutboundEvents events) {
        if (totalQuantity > 0 && orderQuantity > 0) {
            int actualQuantity = (totalQuantity > orderQuantity ? orderQuantity : totalQuantity);
            totalQuantity -= actualQuantity;

            account.orderFilled(orderId, actualQuantity);
            events.orderFilled(accountId, orderId, actualQuantity);
        }
    }
}
