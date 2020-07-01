package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class Machine{
    @Getter Map<String,Integer> outlets;
    @JsonProperty("total_items_quantity")
    @Getter public Map<String,Double> totalItemsQuantity;
    @Getter public Map<String,Map<String,Double>> beverages;
}