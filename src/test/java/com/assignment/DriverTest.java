package com.assignment;

import com.assignment.dto.SetupRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class DriverTest {

    ObjectMapper obj = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    String testCase1;
    Driver driver = new Driver();
    Map<String,Double> addMoreIngridients;


    @Before
    public void setup() {
        try {
            BasicConfigurator.configure();
            testCase1 = FileUtils.readFileToString(new File("src/test/resources/test1.json"));
            SetupRequest setupRequest = obj.readValue(testCase1, SetupRequest.class);
            driver.setUpCoffeeMachine(setupRequest);
            addMoreIngridients = new HashMap<>();
            addMoreIngridients.put("hot_milk",Double.valueOf(1000));
            addMoreIngridients.put("hot_water",Double.valueOf(1000));



        } catch (IOException e) {
            log.info("Please check test file is present");
        }
    }



    @Test
    public void startServing(){
        String[] serveRequests = {"hot_tea","hot_coffee","black_tea","green_tea"};

        driver.serve(serveRequests);

        driver.getCoffeeMachine().checkIngredientsAndThrowWarning();
        driver.getCoffeeMachine().addIngredients(addMoreIngridients);
        driver.serve(serveRequests);


    }

    @Test
    @Ignore
    public void bulkOrder(){
        String[] serveRequests = {"hot_tea","hot_coffee","black_tea","green_tea","green_tea","hot_tea"};
        Map<String,Double> addLotOfIngridient = new HashMap<>();
        addLotOfIngridient.put("hot_water",Double.parseDouble("10000000"));
        addLotOfIngridient.put("hot_milk",Double.parseDouble("10000000"));
        addLotOfIngridient.put("ginger_syrup",Double.parseDouble("10000000"));
        addLotOfIngridient.put("sugar_syrup",Double.parseDouble("10000000"));
        addLotOfIngridient.put("tea_leaves_syrup",Double.parseDouble("10000000"));
        driver.getCoffeeMachine().addIngredients(addLotOfIngridient);
        for(int i = 0 ;i < 1 ; i++){
            driver.serve(serveRequests);
        }
    }





}