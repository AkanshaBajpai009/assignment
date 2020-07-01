package com.assignment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Data
@Slf4j
@Builder
public class Beverage {
    String name;
    Map<String,Double> composition;



}
