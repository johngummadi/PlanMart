package com.planmart.rules;

import com.orderprocessing.Rule;
import com.planmart.*;

import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Created by johngummadi on 6/19/16.
 */
public class TaxRule implements Rule {
    private static final int _taxPercent = 8;
    @Override
    public boolean validateAndApply(Object order) {
        BigDecimal taxAmount = new BigDecimal(0.0);
        Order o = (Order) order;

        /**
         * Orders to nonprofits are exempt from all tax and shipping
         */
        if(o.getCustomer().getIsNonProfit()) {
            /**
             * If non-profit, no line item to add for tax, so return true.
             */
            return true;
        }


        String shippingRegion = o.getShippingRegion();
        for (Iterator iterator = o.getItems().iterator(); iterator.hasNext();) {
            int taxPer = _taxPercent;
            ProductOrder item = (ProductOrder) iterator.next();

            /**
             * Applying the following rule...
             * The following types of items are exempt from tax:
             *      * Food items shipped to CA, NY
             *      * Clothing items shipped to CT
             */
            if ((item.getProduct().getType() == ProductType.FOOD && shippingRegion.matches("CA|NY")) ||
                ((item.getProduct().getType() == ProductType.CLOTHING && shippingRegion == "CT"))) {
                taxPer = 0;
            }


            /**
             * Calculate tax amount now
             */
            double price = item.getProduct().getPrice().doubleValue();
            int quantity = item.getQuantity();
            taxAmount = taxAmount.add(new BigDecimal(((price * quantity) * taxPer) / 100));
        }
        taxAmount = taxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

        /**
         * Now apply tax amount (add a line item)
         */
        LineItem taxItem = new LineItem(LineItemType.TAX, taxAmount);
        o.getLineItems().add(taxItem);


        /**
         * Nothing to validate for the tax rule, so return true
         */
        return true;
    }
}
