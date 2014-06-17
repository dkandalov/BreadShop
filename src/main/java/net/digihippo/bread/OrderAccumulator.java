package net.digihippo.bread;

public class OrderAccumulator {
    private final OutboundEvents events;
    private int totalQuantity;

    public OrderAccumulator(OutboundEvents events) {
        this.events = events;
    }

    public void accumulate(int quantity) {
        totalQuantity += quantity;
    }

    public void finished() {
        events.onWholesaleOrder(totalQuantity);
    }
}
