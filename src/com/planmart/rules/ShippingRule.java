package com.planmart.rules;

import com.orderprocessing.Rule;
import com.planmart.*;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Created by johngummadi on 6/19/16.
 */
public class ShippingRule implements Rule {
    @Override
    public boolean validateAndApply(Object order) {
        BigDecimal shippingAmount = new BigDecimal(0.0);
        BigDecimal totalAmount = new BigDecimal(0.0);
        BigDecimal totalWeight = new BigDecimal(0.0);
        Order o = (Order) order;


        String shippingRegion = o.getShippingRegion();
        Customer customer = o.getCustomer();

        for (Iterator iterator = o.getItems().iterator(); iterator.hasNext();) {
            ProductOrder item = (ProductOrder) iterator.next();

            /**
             * Validate the order with shipping rule
             */
            if (item.getProduct().getType() == ProductType.ALCOHOL) {
                /**
                 * Alcohol may not be shipped to VA, NC, SC, TN, AK, KY, AL
                 */
                if (shippingRegion.matches("VA|NC|SC|TN|AK|KY|AL"))
                    return false;

                /**
                 * Alcohol may only be shipped to customers age 21 or over in the US
                 */
                // TODO: Implement this - Calculate age from DOB, and validate.
            }
            else if (item.getProduct().getType() == ProductType.FOOD) {
                /**
                 * Food may not be shipped to HI
                 */
                if (shippingRegion == "HI")
                    return false;
            }


            /**
             * Now apply calculation(s)
             */
            totalAmount = totalAmount.add(new BigDecimal(item.getProduct().getPrice().doubleValue() * item.getQuantity()));
            totalWeight = totalWeight.add(item.getProduct().getWeight());
        }

        /**
         * Shipping is $10 for orders under 20 pounds in the continental US
         * Shipping is $20 for orders 20 pounds or over in the continental US
         * Shipping for orders to the non-continental US is $35
         */
        if (shippingRegion.matches("PR|VI|PW|MP"))
            shippingAmount = new BigDecimal(35.00);
        else if ((totalWeight.compareTo(new BigDecimal(20.00))==-1)) {
            shippingAmount = new BigDecimal(10.00);
        }
        else if ((totalWeight.compareTo(new BigDecimal(20.00))>=0)) {
            shippingAmount = new BigDecimal(20.00);
        }


        /**
         * Now apply shipping amount (add a line item)
         */
        LineItem shippingItem = new LineItem(LineItemType.SHIPPING, shippingAmount);
        o.getLineItems().add(shippingItem);

        return true;
    }
}
