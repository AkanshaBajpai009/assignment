package com.assignment.utils;

import com.assignment.IngridiantStore;
import com.assignment.dto.Ingredient;

import java.util.*;
import java.util.stream.Collectors;

public class CommonUtils {

    static Random random = new Random();

    public static IngridiantStore makeIngredientStore(Map<String, Double> totalItemsQuantity) {
        IngridiantStore ingridiantStore = IngridiantStore.builder().store(totalItemsQuantity
                .entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(),
                        entry -> Ingredient.builder()
                                .nameOfIngridient(entry.getKey())
                                .quantityOfIngridient(entry.getValue())
                                .build()
                ))).build();
        return ingridiantStore;
    }

    public static int randomNumberSelector(int length) {
        return random.nextInt(length);
    }

    public static String formMessage(Set<String> notAvailableIngrident, Set<String> notSufficientQuantity) {
        String response = "because ";
        String notAvailable = notAvailableIngrident.stream().reduce((i, j) -> i + "," + j).orElse("");
        if (!notAvailable.isEmpty()) return response + "following ingrediants not available " + notAvailable;
        String notSufficient = notSufficientQuantity.stream().reduce((i, j) -> i + "," + j).orElse("");
        return response + "following ingrediants not sufficient " + notSufficient;
    }


    public static Optional<String> createWarningMessage(List<String> ingridientsBelow) {
        if(ingridientsBelow.isEmpty()) return Optional.empty();
        return Optional.of("Runnig low for following ingredient " + ingridientsBelow.stream().reduce((a,b) -> a + "," + b).orElse(""));
    }


}
