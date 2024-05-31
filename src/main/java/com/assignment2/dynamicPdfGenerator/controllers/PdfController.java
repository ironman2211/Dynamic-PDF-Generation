package com.assignment2.dynamicPdfGenerator.controllers;

import com.assignment2.dynamicPdfGenerator.models.InvoiceRequest;
import com.assignment2.dynamicPdfGenerator.services.impl.PdfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class PdfController {

    @Autowired
    private PdfServiceImpl pdfService;

    @PostMapping("/generate-pdf")
    public ResponseEntity<String> generatePdf(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            String pdfPath = pdfService.generatePdf(invoiceRequest);
            return new ResponseEntity<>(pdfPath, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to generate PDF", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam String pdfPath) {
        try {
            byte[] pdfData = pdfService.getPdf(pdfPath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=invoice.pdf");
            return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/generate-and-download-pdf")
    public ResponseEntity<byte[]> generateAndDownloadPdf(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            String pdfPath = pdfService.generatePdf(invoiceRequest);
            byte[] pdfData = pdfService.getPdf(pdfPath);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=invoice.pdf");
            headers.add("Content-Type", "application/pdf");

            return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
