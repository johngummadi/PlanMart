package com.planmart;

import com.orderprocessing.BaseOrderProcessor;
import com.orderprocessing.Rule;
import com.planmart.rules.RewardPointsRule;
import com.planmart.rules.ShippingRule;
import com.planmart.rules.TaxRule;

import java.util.ArrayList;

public class PlanMartOrderProcessor extends BaseOrderProcessor {

    /**
     * The Constructor
     */
    public PlanMartOrderProcessor() {
        _rules = new ArrayList<>();

        /**
         * Add all rules for the processor here
         */
        _rules.add(new TaxRule());
        _rules.add(new ShippingRule());
        _rules.add(new RewardPointsRule());
    }


    /**
     * Entry point for the interface.
     * This is the entry point we expect you to implement.
     * Remember, favor design that is maintainable, easy to read, and easy
     * to extend in the future as the requirements and rules change  
     */
    @Override
    public boolean processOrder(Object order) {
        return super.processOrder(order);
    }
}
