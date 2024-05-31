package com.assignment2.dynamicPdfGenerator.models;

import lombok.Data;
import java.util.List;

@Data
public class InvoiceRequest {
    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;
    private List<Item> items;
}
