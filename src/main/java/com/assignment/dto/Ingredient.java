package com.assignment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ingredient  {
    public String nameOfIngridient;
    public Double quantityOfIngridient;

    public boolean getIngridient(Double amount){
        boolean ans = quantityOfIngridient >= amount ? true : false;
        quantityOfIngridient -= amount;
        return ans;
    }

    public boolean hasIngridient(Double amount){
        return quantityOfIngridient >= amount;
    }

    public void addIngridient(Double amount){
        quantityOfIngridient+=amount;
    }

    public boolean isBelowWarning(Double quantity){
        return quantityOfIngridient < quantity;
    }


}
