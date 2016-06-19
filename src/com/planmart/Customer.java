package com.planmart;

import java.util.Date;


public class Customer {

    private boolean isNonProfit;
    private Date birthDate;
    
    public Customer(Date birthDate, boolean isNonProfit) {
        this.birthDate = birthDate;
        this.isNonProfit = isNonProfit;
    }
    
    /**
     * Does the customer represent a non-profit organization
     */
    public boolean getIsNonProfit() {
        return isNonProfit;
    }
    
    /**
     * The date of birth of the customer
     */
    public Date getBirthDate() {
        return birthDate;
    }

}
