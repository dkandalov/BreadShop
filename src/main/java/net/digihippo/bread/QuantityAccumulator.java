package net.digihippo.bread;

public class QuantityAccumulator {
    private final OutboundEvents events;
    private int totalQuantity;

    public QuantityAccumulator(OutboundEvents events) {
        this.events = events;
    }

    public void accumulate(int quantity) {
        totalQuantity += quantity;
    }

    public void finished() {
        events.onWholesaleOrder(totalQuantity);
    }
}
