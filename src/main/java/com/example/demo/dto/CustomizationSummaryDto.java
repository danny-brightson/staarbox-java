package com.example.demo.dto;

import java.util.Map;

public class CustomizationSummaryDto {

    private int totalCustomers;
    private Map<String, Long> fruitCounts;
    private Map<String, Long> optionalCounts;
    private int customizedCustomers;
   
    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Map<String, Long> getFruitCounts() {
        return fruitCounts;
    }

    public void setFruitCounts(Map<String, Long> fruitCounts) {
        this.fruitCounts = fruitCounts;
    }

    public int getCustomizedCustomers() {
        return customizedCustomers;
    }

    public void setCustomizedCustomers(int customizedCustomers) {
        this.customizedCustomers = customizedCustomers;
    }

    public Map<String, Long> getOptionalCounts() {
        return optionalCounts;
    }

    public void setOptionalCounts(Map<String, Long> optionalCounts) {
        this.optionalCounts = optionalCounts;
    }
}