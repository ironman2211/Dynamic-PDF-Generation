package com.assignment2.dynamicPdfGenerator.controllers;

import com.assignment2.dynamicPdfGenerator.models.InvoiceRequest;
import com.assignment2.dynamicPdfGenerator.services.impl.PdfServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PdfController.class)
class PdfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PdfServiceImpl pdfService;

    private InvoiceRequest invoiceRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setSeller("XYZ Pvt. Ltd.");
        invoiceRequest.setSellerGstin("29AABBCCDD121ZD");
        invoiceRequest.setSellerAddress("New Delhi, India");
        invoiceRequest.setBuyer("Vedant Computers");
        invoiceRequest.setBuyerGstin("29AABBCCDD131ZD");
        invoiceRequest.setBuyerAddress("New Delhi, India");
    }

    @Test
    void testGeneratePdf() throws Exception {
        when(pdfService.generatePdf(any(InvoiceRequest.class))).thenReturn("invoices/test.pdf");

        mockMvc.perform(post("/api/generate-pdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"seller\":\"XYZ Pvt. Ltd.\",\"sellerGstin\":\"29AABBCCDD121ZD\",\"sellerAddress\":\"New Delhi, India\",\"buyer\":\"Vedant Computers\",\"buyerGstin\":\"29AABBCCDD131ZD\",\"buyerAddress\":\"New Delhi, India\",\"items\":[{\"name\":\"Product 1\",\"quantity\":\"12 Nos\",\"rate\":123.00,\"amount\":1476.00}]}"))
                .andExpect(status().isOk())
                .andExpect(content().string("invoices/test.pdf"));
    }

    @Test
    void testDownloadPdf() throws Exception {
        byte[] pdfContent = "Sample PDF content".getBytes();
        when(pdfService.getPdf("invoices/test.pdf")).thenReturn(pdfContent);

        mockMvc.perform(get("/api/download-pdf")
                        .param("pdfPath", "invoices/test.pdf"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=invoice.pdf"))
                .andExpect(content().contentType("application/octet-stream"))
                .andExpect(content().bytes(pdfContent));
    }

    @Test
    void testGenerateAndDownloadPdf() throws Exception {
        when(pdfService.generatePdf(any(InvoiceRequest.class))).thenReturn("invoices/test.pdf");
        byte[] pdfContent = "Sample PDF content".getBytes();
        when(pdfService.getPdf("invoices/test.pdf")).thenReturn(pdfContent);

        mockMvc.perform(post("/api/generate-and-download-pdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"seller\":\"XYZ Pvt. Ltd.\",\"sellerGstin\":\"29AABBCCDD121ZD\",\"sellerAddress\":\"New Delhi, India\",\"buyer\":\"Vedant Computers\",\"buyerGstin\":\"29AABBCCDD131ZD\",\"buyerAddress\":\"New Delhi, India\",\"items\":[{\"name\":\"Product 1\",\"quantity\":\"12 Nos\",\"rate\":123.00,\"amount\":1476.00}]}"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=invoice.pdf"))
                .andExpect(content().contentType("application/pdf"))
                .andExpect(content().bytes(pdfContent));
    }
}
