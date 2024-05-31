package com.assignment2.dynamicPdfGenerator.models;

import lombok.Data;

@Data
public class Item {
    private String name;
    private String quantity;
    private double rate;
    private double amount;
}