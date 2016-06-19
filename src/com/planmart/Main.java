package com.planmart;

import sun.plugin.dom.exception.InvalidStateException;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.Optional;

public class Main {
    /**
     * Note:
     * We recognize that the practice of having tests in main is poor
     * but is done here this way for simplicity. 
     * You are welcome to use JUnit, or any other testing framework.
     * You are also welcome to place all tests here - we mostly care that
     *  a) your design of the OrderProcessor is clean/extensibile/maintainable
     *  b) that your implementation is tested in one way or another
     * 
     */
    public static void main(String[] args) {
        Order order = new Order(
            new Customer(new GregorianCalendar(1988, 3, 4).getTime(), false),
            "AZ",
            PaymentMethod.VISA,
            new GregorianCalendar(2015, 11, 27).getTime());
        order.getItems().add(new ProductOrder(new Product(new BigDecimal(20), new BigDecimal(2), ProductType.ALCOHOL), 3));
        order.getItems().add(new ProductOrder(new Product(new BigDecimal(25), new BigDecimal(3), ProductType.FOOD), 3));

        PlanMartOrderProcessor processor = new PlanMartOrderProcessor();
        boolean isValid = processor.processOrder(order);
        Optional<LineItem> tax = order.getLineItems().stream().filter(x -> x.getType() == LineItemType.TAX).findFirst();
        Optional<LineItem> shipping = order.getLineItems().stream().filter(x -> x.getType() == LineItemType.SHIPPING).findFirst();
        Optional<LineItem> rewardPoints = order.getLineItems().stream().filter(x -> x.getType() == LineItemType.REWARDS_POINTS).findFirst();

        Assert(isValid);
        Assert(tax.isPresent());
        Assert(new BigDecimal(10.80).equals(tax.get().getAmount()));
        Assert(shipping.isPresent());
        Assert(new BigDecimal(10).equals(shipping.get().getAmount()));
        Assert(rewardPoints.isPresent());
        Assert(new BigDecimal(134).equals(rewardPoints.get().getAmount()));
    }

    private static void Assert(boolean condition) {
        if (!condition) {
            throw new InvalidStateException("Assertion failed");
        }
    }
}
