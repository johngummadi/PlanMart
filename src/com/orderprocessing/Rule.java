package com.orderprocessing;


/**
 * Created by johngummadi on 6/18/16.
 *
 * This interface provides a contract for custom Rules to validate and apply a 'rule' logic could modify the 'order'.
 * Each Order Processors will have a set of 'Rules' that applies on orders for that particular company/processor.
 */
public interface Rule {
    /**
     * Validates the order according to this rule, and if it is valid,
     * it will apply changes to the order (like adding line items, applying
     * discounts, reward points, etc.,)
     * @param order The order on which the rule should be applied on.
     *              The implementor should typecast the Object to its
     *              custom Order class, and update the order according to the rule.
     *              NOTE: Ideally we should have interfaces to all these classes
     *              like Order, Customer, etc., so multiple processors could have
     *              uniform interface, so it'll be easy to plug new companies to
     *              this system.
     * @return true is order is valid per rule, else false.
     */
    boolean validateAndApply(Object order);
}
