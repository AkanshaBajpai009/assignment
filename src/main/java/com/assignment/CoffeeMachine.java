package com.assignment;


import com.assignment.dto.Beverage;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.nio.file.Watchable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Task 1 -> Complete full flow single threaded ;
 * Task 2 -> Have some implementation of ThreadPoolExecutorWorking
 * <p>
 * <p>
 * And this is only for learning purpose
 */

@Builder
@Data
@Slf4j
public class CoffeeMachine {
    Integer numberOfOutlets;
    IngridiantStore ingridiantStore;
    Double lowerBar;
    ThreadPoolExecutor outletsServing;

    public void serveBeverage(List<Beverage> beverages) {
        CompletableFuture<Void>[] futures = new CompletableFuture[beverages.size()];
        for(int i = 0 ;i < beverages.size() ;i++){
            Beverage beverage = beverages.get(i);
            futures[i] = CompletableFuture.runAsync(() -> serveBeverage(beverage),outletsServing);
        }

        CompletableFuture.allOf(futures).whenComplete((i,j) -> {}).join();
    }


    public boolean serveBeverage(Beverage beverage) {
        Map<String, Double> composition = beverage.getComposition();
        log.info("Got order of beverage {} and serving it on outlet name {}",beverage.getName(),Thread.currentThread().getName());
        Optional<String> message = ingridiantStore.hasAllIngridents(composition);
        if (message.isPresent()) {
            log.info(beverage.getName() + " cannot be prepared " + message.get());
            return false;
        }
        ingridiantStore.getAllTheIngridents(composition);
        log.info(beverage.getName() + " is prepared");
        return true;
    }

    public void checkIngredientsAndThrowWarning() {
        Optional<String> warnings = ingridiantStore.warnings(lowerBar);
        if (warnings.isPresent()) log.info(warnings.get());
    }

    public void addIngredients(Map<String, Double> toAdd) {
        ingridiantStore.addIngredients(toAdd);
    }


}
