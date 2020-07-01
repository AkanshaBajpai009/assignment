package com.assignment;

import com.assignment.dto.Beverage;

import com.assignment.dto.SetupRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.assignment.utils.CommonUtils.makeIngredientStore;


@Data
@Slf4j()
public class Driver {

    CoffeeMachine coffeeMachine;
    Map<String,Map<String,Double>> beverageStore;
    Object[] availableBeverages;
    Integer serveable;

    public void setUpCoffeeMachine(SetupRequest setupRequest) {
        int corePoolSize  = Math.min(10,setupRequest.getMachine().getOutlets().get("count_n"));
        int maxPoolSize = Math.min(10,setupRequest.getMachine().getOutlets().get("count_n"));
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,maxPoolSize,3, TimeUnit.SECONDS,new ArrayBlockingQueue<>(corePoolSize*5));


        coffeeMachine = CoffeeMachine.builder()
                .numberOfOutlets(setupRequest.getMachine().getOutlets().get("count_n"))
                .ingridiantStore(makeIngredientStore(setupRequest.getMachine().getTotalItemsQuantity()))
                .lowerBar(Double.valueOf(10))
                .outletsServing(executor)
                .build();

        beverageStore = setupRequest.getMachine().getBeverages();

        availableBeverages = beverageStore.keySet().stream().collect(Collectors.toList()).toArray();

        serveable = availableBeverages.length;


    }



    public boolean serve(String beverage){
        Map<String, Double> composition =  beverageStore.get(beverage);
        boolean served = coffeeMachine.serveBeverage(Beverage.builder().name(beverage).composition(beverageStore.get(beverage)).build());
        if(served) log.info(beverage + " is prepared");
        return served;
    }

    public boolean serve(String... beverage){
        List<Beverage> beveragesToBeServed;
        beveragesToBeServed = Arrays.stream(beverage).map(it -> Beverage.builder().composition(beverageStore.get(it)).name(it).build()).collect(Collectors.toList());
        coffeeMachine.serveBeverage(beveragesToBeServed);
        return true;
    }




}
