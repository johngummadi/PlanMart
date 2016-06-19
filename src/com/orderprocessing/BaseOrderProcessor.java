package com.orderprocessing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Abstract base class that implements OrderProcessor.
 * This class implements basic functionality like adding Rules,
 * and applying rules, there by enforcing application and
 * validation of rules.
 */
public abstract class BaseOrderProcessor implements OrderProcessor {
    protected ArrayList<Rule> _rules;

    /**
     * Processes and validates the order.  An order should have zero or more line items added to it, and
     * should return a boolean status indicating whether the order is valid.
     * @param order The order being processed.  The only modifications to the order should be to the line items property.
     * @return true if the order is valid, false if something about the order breaks a rule.
     */
    public boolean processOrder(Object order) {
        /**
         * If no rules are provided, assume the order is valid
         */
        if (_rules == null)
            return true;

        /**
         * Now iterate through all the rules, and validate & apply them.
         */
        for (Iterator<Rule> iterator = _rules.iterator(); iterator.hasNext();) {
            Rule rule = iterator.next();
            if (! rule.validateAndApply(order))
                return false;
        }
        return true;
    }
}