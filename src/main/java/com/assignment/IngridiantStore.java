package com.assignment;

import com.assignment.dto.Ingredient;
import com.assignment.utils.CommonUtils;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.util.*;

import static java.util.Optional.of;

@Builder
@Data
@Slf4j
/***
 *
 *
 */
public class IngridiantStore {
    public Map<String, Ingredient> store;


//
    public synchronized Optional<String> hasAllIngridents(Map<String,Double> composition){
        Set<String> ingridents = composition.keySet();
        Set<String> notAvailableIngrident = new HashSet<>();
        Set<String> notSufficientQuantity = new HashSet<>();
        for (String ingridient : ingridents){
            if(store.containsKey(ingridient)){
                Ingredient ingredientObj = store.get(ingridient);
                if (!ingredientObj.hasIngridient(composition.get(ingridient))){
                    notSufficientQuantity.add(ingridient);
                }
            }
            else{
            //Ingridients is not available
                notAvailableIngrident.add(ingridient);
            }
        }
        if(notAvailableIngrident.isEmpty() && notSufficientQuantity.isEmpty()) return Optional.empty();
        else {
           return Optional.of(CommonUtils.formMessage(notAvailableIngrident,notSufficientQuantity));
        }
    }

    public synchronized void getAllTheIngridents(Map<String, Double> composition) {
        Set<String> ingridents = composition.keySet();
        for(String ingridient : ingridents) {
            store.get(ingridient).getIngridient(composition.get(ingridient));
        }
    }

    public synchronized Optional<String> warnings(Double amount){
        List<String> ingridientsBelow = new ArrayList<>();
        for(String ing : store.keySet()){
            if (store.get(ing).isBelowWarning(amount)){
                ingridientsBelow.add(ing);
            }
        }
        return CommonUtils.createWarningMessage(ingridientsBelow);

    }

    public synchronized void addIngredients(Map<String, Double> toAdd) {
        for(String ingredients : toAdd.keySet()){
            if(store.containsKey(ingredients)){
                store.get(ingredients).addIngridient(toAdd.get(ingredients));
                log.info("Successfully Added Ingridient {}",ingredients);
            }
            else {
                log.info("You are adding unsupported ingridient {}",ingredients);
            }
        }
    }
}
