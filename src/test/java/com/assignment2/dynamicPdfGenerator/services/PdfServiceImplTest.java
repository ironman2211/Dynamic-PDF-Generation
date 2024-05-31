package com.assignment2.dynamicPdfGenerator.services;

import com.assignment2.dynamicPdfGenerator.models.InvoiceRequest;
import com.assignment2.dynamicPdfGenerator.services.impl.PdfServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PdfServiceImplTest {

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private PdfServiceImpl pdfService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratePdf() throws IOException {
        // Prepare test data
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setSeller("seller");
        invoiceRequest.setSellerGstin("29AABBCCDD121ZD");
        invoiceRequest.setSellerAddress("New Delhi, India");
        invoiceRequest.setBuyer("Vedant Computers");
        invoiceRequest.setBuyerGstin("29AABBCCDD131ZD");
        invoiceRequest.setBuyerAddress("New Delhi, India");
        // Add items if necessary

        // Mock template processing
        when(templateEngine.process(any(String.class), any(Context.class)))
                .thenReturn("<html><body>Mock PDF content</body></html>");

        // Call the method to test
        String pdfPath = pdfService.generatePdf(invoiceRequest);

        // Validate the results
        assertNotNull(pdfPath);
        Path path = Path.of(pdfPath);
        assertTrue(Files.exists(path), "PDF file should be created");

        // Cleanup
        Files.deleteIfExists(path);
    }

    @Test
    void testGetPdf() throws IOException {
        // Prepare a temporary PDF file
        String pdfContent = "Sample PDF content";
        Path tempPdfPath = Files.createTempFile("test", ".pdf");
        Files.write(tempPdfPath, pdfContent.getBytes());

        // Call the method to test
        byte[] pdfData = pdfService.getPdf(tempPdfPath.toString());

        // Validate the results
        assertNotNull(pdfData);
        assertArrayEquals(pdfContent.getBytes(), pdfData);

        // Cleanup
        Files.deleteIfExists(tempPdfPath);
    }
}
